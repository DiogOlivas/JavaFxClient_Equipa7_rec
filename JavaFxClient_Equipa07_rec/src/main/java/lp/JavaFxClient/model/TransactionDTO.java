package lp.JavaFxClient.model;

public class TransactionDTO {
    private Long id;
    private String destination;
    private String date;
    private double value;
    private String paymentMethod;
    private String category;
    private Long userId;
    private String description;

    public TransactionDTO(){}

    public TransactionDTO(Long id, String destination, String date, double value, String paymentMethod, String category, String description, Long userId){
        this.id = id;
        this.destination = destination;
        this.date = date;
        this.value = value;
        this.paymentMethod = paymentMethod;
        this.category = category;
        this.description = description;
        this.userId = userId;
    }

    public Long getId(){
         return id; 
        }
    public void setId( Long id ){ 
        this.id = id;
    }

    public String getDestination(){
         return destination;
    }
    public void setDestination( String destination ){ 
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
