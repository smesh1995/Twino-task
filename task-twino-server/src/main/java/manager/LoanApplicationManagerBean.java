package manager;

import loanapplication.LoanApplication;
import status.StatusEnum;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by sandro on 2/11/17.
 */
@Stateless
public class LoanApplicationManagerBean implements LoanApplicationManager {

    @PersistenceContext(name = "twino")
    private EntityManager em;

    @Override
    public void createApplication(LoanApplication application) {
        em.persist(application);
    }

    @Override
    public void deleteApplication(long id) throws Exception {
        LoanApplication ap = em.find(LoanApplication.class, id);
        if (ap != null){
            em.remove(ap);
        } else {
            throw new Exception("application wth id: " + id + "does not exists");
        }
    }

    @Override
    public List<LoanApplication> getApplication(Long userId, String firstName, String lastNAme, String employer, String birthDate, Integer salary, Integer perMonth, Integer amount, String term) {
        return getApplication(userId,firstName,lastNAme,employer,birthDate,salary,perMonth,amount,term,null);
    }

    @Override
    public List<LoanApplication> getApplication(Long userId,
                                                String firstName,
                                                String lastName,
                                                String employer,
                                                String birthDate,
                                                Integer salary,
                                                Integer perMonth,
                                                Integer amount,
                                                String term,
                                                StatusEnum status) {
        String queryString = "SELECT ap FROM LoanApplication ap WHERE 1=1 ";
        Map<String, Object> map = new HashMap<>();

        if (userId != null){
            queryString+= " And ap.userId = :userId ";
            map.put("userId", userId);
        }

        if (firstName != null){
            queryString+= " And ap.firstName = :firstName ";
            map.put("firstName", firstName);
        }

        if (lastName != null){
            queryString+= " And ap.lastName = :lastName ";
            map.put("lastName", lastName);
        }

        if (employer != null){
            queryString+= " And ap.employer = :employer ";
            map.put("employer", employer);
        }

        if (birthDate != null){
            queryString+= " And ap.birthDate = :birthDate ";
            map.put("birthDate", birthDate);
        }

        if (salary != null){
            queryString+= " And ap.salary = :salary ";
            map.put("salary", salary);
        }

        if (perMonth != null){
            queryString+= " And ap.perMonth = :perMonth ";
            map.put("perMonth", perMonth);
        }

        if (amount != null){
            queryString+= " And ap.reqAmount = :amount ";
            map.put("amount", amount);
        }

        if (term != null){
            queryString+= " And ap.reqTerm = :term ";
            map.put("term", term);
        }

        if (status != null){
            queryString+= " And ap.status = :status ";
            map.put("status", status);
        }

        Query query = em.createQuery(queryString);

        for (Map.Entry<String, Object> param : map.entrySet()){
            query.setParameter(param.getKey(), param.getValue());
        }
        return query.getResultList();
    }

    @Override
    public void markApplication(long id, StatusEnum status) throws Exception {
        LoanApplication ap = em.find(LoanApplication.class, id);
        if (ap == null){
            throw new Exception("application wth id: "+id  + "does not exists");
        }else if (ap.getStatus() == StatusEnum.MANUAL){
            ap.setStatus(status);
        }else {
            throw new Exception("application with id: " +id +" already has status:  "+ ap.getStatus());
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void resolveApplication(LoanApplication application, StatusEnum status, int score) throws Exception {
        LoanApplication ap = em.find(LoanApplication.class, application.getId());
        if (ap != null){
            ap.setScore(score);
            ap.setStatus(status);
        } else {
            throw new Exception("application wth id: " + application.getId() + "does not exists");
        }
    }


}
