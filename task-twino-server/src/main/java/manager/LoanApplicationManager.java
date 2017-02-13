package manager;

import loanapplication.LoanApplication;
import status.StatusEnum;

import java.util.Date;
import java.util.List;

/**
 * Created by sandro on 2/11/17.
 */
public interface LoanApplicationManager {
    void createApplication(LoanApplication application);
    void deleteApplication(long id) throws Exception;
    List<LoanApplication> getApplication(Long userId,
                                         String firstName,
                                         String lastNAme,
                                         String employer,
                                         String birthDate,
                                         Integer salary,
                                         Integer perMonth,
                                         Integer amount,
                                         String term);
    List<LoanApplication> getApplication(Long userId,
                                         String firstName,
                                         String lastNAme,
                                         String employer,
                                         String birthDate,
                                         Integer salary,
                                         Integer perMonth,
                                         Integer amount,
                                         String term,
                                         StatusEnum status);

    void markApplication(long id, StatusEnum status) throws Exception;
    void resolveApplication(LoanApplication application,StatusEnum status, int score) throws Exception;

}
