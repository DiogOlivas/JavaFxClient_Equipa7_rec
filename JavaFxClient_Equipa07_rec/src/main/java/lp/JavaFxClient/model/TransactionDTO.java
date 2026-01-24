package lp.JavaFxClient.model;

import java.util.List;

public class TransactionDTO {
	private Long id;
    private double value;
    private String date;
    private String description;
    private Long userId;
    private String paymentMethod; 
    private List<Long> categoryIds; //list of category IDs 
    

    public TransactionDTO() {}

    public TransactionDTO(Long id, double value, String date, String description, Long userId,String paymentMethod, List<Long> categoryIds) {
        this.id = id;
        this.value = value;
        this.description = description;
        this.userId = userId;
        this.date = date;
        this.paymentMethod = paymentMethod;
        this.categoryIds = categoryIds;
    }
    //
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public double getValue(){
        return value;
    }
    public void setValue(double value){
        this.value = value;
    }
    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public Long getUserId(){
        return userId;
    }
    public void setUserId(Long userId){
        this.userId = userId;
    }
    public List<Long> getCategoryIds() {
        return categoryIds;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

}
