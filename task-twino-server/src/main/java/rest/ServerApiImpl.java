package rest;
import loanapplication.LoanApplication;
import manager.LoanApplicationManager;
import service.ServerApi;
import status.StatusEnum;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

/**
 * Created by sandro on 2/12/17.
 */
@Stateless
@Remote(ServerApi.class)
public class ServerApiImpl implements ServerApi {

    @Inject
    private LoanApplicationManager applicationManager;

    @Override
    public void createLoanApplication(long userId, String firstName, String lastName, String birthDate, String employer, int salary, int permonth, int amount, String term) {
        LoanApplication application = new LoanApplication();
        application.setStatus(StatusEnum.UNRESOLVED);
        application.setFirstName(firstName);
        application.setLastName(lastName);
        application.setScore(0);
        application.setBirthDate(birthDate);
        application.setUserId(userId);
        application.setPerMonth(permonth);
        application.setReqAmount(amount);
        application.setReqTerm(term);
        application.setEmployer(employer);
        application.setSalary(salary);
        applicationManager.createApplication(application);
    }

    @Override
    public String deleteLoanApplication(long id) {
        try {
            applicationManager.deleteApplication(id);
            return "Successfully deleted";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public List<LoanApplication> list(Long userId,
                                      String firstName,
                                      String lastName,
                                      String birthDate,
                                      String employer,
                                      Integer salary,
                                      Integer permonth,
                                      Integer amount,
                                      String term) {
        List<LoanApplication> list = applicationManager.getApplication(userId,firstName,lastName,employer,
                birthDate,salary,permonth,amount,term);

        return list;
    }

    @Override
    public String mark(long id, StatusEnum status) {
        try {
            applicationManager.markApplication(id,status);
            return  "status has successfully updated";
        } catch (Exception e) {
            return  e.getMessage();
        }
    }

    @Override
    public String test() {
        return "Zazaza";
    }
}
