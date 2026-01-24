package lp.JavaFxClient.model;



public class TransactionDTO {
	private Long id;
    private double value;
    private String date;
    private String description; 
    private Long userId; 
    private String paymentMethod;
    private String category; //list of category IDs 
    

    public TransactionDTO() {}

    public TransactionDTO(Long id, double value, String date, String description, Long userId,String paymentMethod, String category) {
        this.id = id;
        this.value = value;
        this.description = description;
        this.userId = userId;
        this.date = date;
        this.paymentMethod = paymentMethod;
        this.category = category;
    }
    
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
    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public String getPaymentMethod(){
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod){
        this.paymentMethod = paymentMethod;
    }

}
