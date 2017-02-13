package service;

import loanapplication.LoanApplication;
import status.StatusEnum;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

/**
 * Created by sandro on 2/12/17.
 */
@Path("/")
public interface ServerApi {

	@POST
	@Path("createLoanApplication/{userId}/{firstName}/{lastName}/{birthDate}/{employer }/{salary}/{permonth}/{amount}/{term}")
	@Produces(MediaType.APPLICATION_JSON)
	void createLoanApplication(
			@PathParam("userId") long userId,
			@PathParam("firstName") String firstName,
			@PathParam("lastName") String lastName,
			@PathParam("birthDate") String birthDate,
			@PathParam("employer") String employer,
			@PathParam("salary") int salary,
			@PathParam("permonth") int permonth,
			@PathParam("amount") int amount,
			@PathParam("term") String term);

	@POST
	@Path("deleteLoanApplication/{loanApplicationId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	String deleteLoanApplication(
			@PathParam("loanApplicationId") long id);

	@GET
	@Path("list/{userId}/{firstName}/{lastName}/{birthDate}/{employer }/{salary}/{permonth}/{amount}/{term}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	List<LoanApplication> list(
			@PathParam("userId") Long userId,
			@PathParam("firstName") String firstName,
			@PathParam("lastName") String lastName,
			@PathParam("birthDate") String birthDate,
			@PathParam("employer") String employer,
			@PathParam("salary") Integer salary,
			@PathParam("permonth") Integer permonth,
			@PathParam("amount") Integer amount,
			@PathParam("term") String term);

	@POST
	@Path("mark/{loanApplicationId}/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	String mark(@PathParam("loanApplicationId") long id, @PathParam("status") StatusEnum status);

	@GET
	@Path("test")
	@Produces(MediaType.APPLICATION_JSON)
	String test();
}
