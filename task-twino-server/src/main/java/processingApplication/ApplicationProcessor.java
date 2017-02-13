package processingApplication;

import loanapplication.LoanApplication;
import manager.LoanApplicationManager;
import status.StatusEnum;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sandro on 2/11/17.
 */
@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class ApplicationProcessor {

    private ExecutorService executor;

    @Inject
    private LoanApplicationManager appManager;

    @Resource
    private TimerService timer;

    @PostConstruct
    private void startUp(){
        TimerConfig cfg = new TimerConfig( "Processing LoanApplication", false);
        timer.createIntervalTimer(0,60000, cfg);
    }

    @Timeout
    private void resolveApplications(){
        List<LoanApplication> resultList=appManager.getApplication(null,
                null,null,null,
                null,null,null,
                null,null, StatusEnum.UNRESOLVED);
        executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i< resultList.size(); i+=resultList.size()/5){
            executor.execute(new Processor(resultList.subList(i,
                    Math.min(resultList.size()-i, i+resultList.size()/5))));
        }
    }

    private class Processor implements Runnable{

        private List<LoanApplication> list;

        public Processor(List<LoanApplication> list){
            this.list = list;
        }

        private void setApplicationStatus(LoanApplication application){
            int score = calculateScore(application);

            if (score<4500){
                application.setStatus(StatusEnum.REJECT);
            }

            if (score>5000){
                application.setStatus(StatusEnum.APPROVE);
            }

            if (score >=4500 && score <=5000){
                application.setStatus(StatusEnum.MANUAL);
            }
            try {
                appManager.resolveApplication(application, application.getStatus(),score);
            } catch (Exception e) {}
        }

        private int calculateScore(LoanApplication application){
            int score=0;
            String lastName = application.getLastName();
            int sumOfsymbols=0;

            for (char symbol :lastName.toCharArray()){
                sumOfsymbols += symbol-'a';
            }
            String date =application.getBirthDate();
            String[] regExp =date.split("-");
            Calendar cal = Calendar.getInstance();
            cal.set(Integer.parseInt(regExp[2]),Integer.parseInt(regExp[1]), Integer.parseInt(regExp[0]));
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH)+1;
            score += sumOfsymbols + application.getSalary()*2 - application.getPerMonth()*3
                    + year -month -cal.get(Calendar.DAY_OF_YEAR);
            return score;
        }

        @Override
        public void run() {
            for (LoanApplication application :list){
                setApplicationStatus(application);
            }
        }
    }
}
