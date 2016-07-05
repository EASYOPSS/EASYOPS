package com.tsoap.sat.businessobject;

import com.parse.ParseObject;
import com.tsoap.sat.utils.EasyOpsUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Created by nisheeth on 01/11/15.
 */
public class Expense extends BaseLogging{

   // SimpleDateFormat dateformat3 = new SimpleDateFormat("dd/MM/yyyy");


  // private ExpenseCategory expensetype;

    public String getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(String expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    private String expenseCategory;
   // private RouteDetails route;
    private double expenseAmount;
    private String Description;
    private byte[] attachment;

    public Expense(){
        super();
    }

    public Expense(ParseObject obj){
        super(obj);
        this.setExpenseCategory(obj.getString("EXPENSECATEGORY"));
        this.setExpenseAmount(obj.getDouble("EXPENSEAMOUNT"));
        this.setDescription(obj.getString("DESCRIPTION"));
        //this.setCreatedOn(obj.getString("CREATEDON"));

    }

  /*  public ExpenseCategory getExpensetype() {
        return expensetype;
    }

    public void setExpensetype(ExpenseCategory expensetype) {
        this.expensetype = expensetype;
    }*/

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }


   /* public RouteDetails getRoute() {
        return route;
    }

    public void setRoute(RouteDetails route) {
        this.route = route;
    }
*/

    @Override
    public BaseLogging getPojoObject(ParseObject obj){
        //this.setUser(obj.getString("USER"));
       // this.setRoute((RouteDetails) new RouteDetails().getPojoObject(obj.getParseObject("ROUTE")));
       // this.setExpensetype(obj.getString("EXPENSE_CATEGORY"));
        this.setExpenseAmount(obj.getDouble("EXPENSEAMOUNT"));
        this.setDescription(obj.getString("DESCRIPTION"));
        //this.setCreatedOn(obj.getString("CREATEDON"));
        return this;
    }

    @Override
    public void setUpJsonData()  {
        parseObject = new ParseObject(EasyOpsUtil.COLLECTION_NAME.EXPENSE.toString());
        parseObject.put("EXPENSEAMOUNT", this.getExpenseAmount());
        parseObject.put("DESCRIPTION", this.getDescription());
        //parseObject.put("EXPENSETYPE", new ExpenseCategory());
        parseObject.put("EXPENSECATEGORY",this.getExpenseCategory());

    }
}
