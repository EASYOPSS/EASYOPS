package com.tsoap.sat.businessobject;

import com.parse.ParseObject;
import com.tsoap.sat.utils.EasyOpsUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nisheeth on 01/11/15.
 */
public class ExpenseCategory extends BaseLogging{



    private String expenseId;
    private String colorcode;
    private String expenseCategory;
    private String createdOn;


    public ExpenseCategory(){
        super();
    }

    public ExpenseCategory(ParseObject obj){
        super(obj);
        this.setColorcode(obj.getString("COLORCODE"));
        this.setExpenseCategory(obj.getString("EXPENSECATEGORY"));
    }

    public String getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(String expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public String getColorcode() {
        return colorcode;
    }

    public void setColorcode(String colorcode) {
        this.colorcode = colorcode;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }



    @Override
    public BaseLogging getPojoObject(ParseObject obj)  {
        this.setColorcode(obj.getString("COLORCODE"));
        this.setExpenseCategory(obj.getString("EXPENSECATEGORY"));
        //this.setUser(obj.getString("USER"));
        return this;
    }

    @Override
    protected void setUpJsonData(){
        parseObject = new ParseObject(EasyOpsUtil.COLLECTION_NAME.EXPENSE_CATEGORY.toString());
        parseObject.put("COLORCODE", this.getColorcode());
        parseObject.put("EXPENSECATEGORY", this.getExpenseCategory());
    }


}
