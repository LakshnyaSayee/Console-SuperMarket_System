import java.util.*;
import java.time.*;
public class SuperMarket {
    static class product{
    int id;
    String name;
    double cost;
    int quantity;
    int sold=0;
    product(int id,String name,double cost,int quantity){
        this.id=id;
        this.name=name;
        this.cost=cost;
        this.quantity=quantity;
    }
    }
    static class cart{
        product p;
        int qty;
        cart(product p,int qty){
            this.p=p;
            this.qty=qty;
        }
    }
    static class customer{
        String email,password;
        double credit=1000;
        int points=0;
        List<cart>cartItem=new ArrayList<>();
        List<String>history=new ArrayList<>();
        customer(String email,String password){
            this.email=email;
            this.password=password;
        }
    }
    static Scanner sc=new Scanner(System.in);
    static HashMap<Integer,product>products=new HashMap<>();
    static HashMap<String,customer>customers=new HashMap<>();
    static String admin_email="admin@gmail.com";
    static String admin_pass="admin";
    public static void main(String args[]){
        seed();
        while(true){
        System.out.println("Welcome to Super Market System");
        System.out.println("1.Admin Login");
        System.out.println("2.Customer Login");
        System.out.println("3.Exit");
        System.out.println("Enter your choice:");
        int c=sc.nextInt();
        sc.nextLine();
        switch(c){
            case 1:
                System.out.println("Enter the admin mail:");
                String mail=sc.nextLine();
                System.out.println("Enter the admin password:");
                String pass=sc.nextLine();
                if(mail.equals(admin_email)&&pass.equals(admin_pass)){
                    adminmenu();
                    break;
                }
                else{
                    System.out.println("Invalid");
                }
                break;
            case 2:
                System.out.println("Enter the username:");
                String user=sc.nextLine();
                System.out.println("Enter the password");
                String pw=sc.nextLine();
                customer cus =customers.get(user);
                if(cus!=null &&cus.password.equals(pw)){
                    customermenu(cus);
                }
                else{
                    System.out.println("Invalid");
                }
                break;
            case 3:
                System.exit(0);
        }
    }
}
static void seed(){
    products.put(1,new product(1, "MILK", 23, 35));
    products.put(2,new product(2, "CHOCOLATE", 100, 25));
    products.put(3,new product(3, "OIL", 70, 40));
    products.put(4,new product(4, "RICE", 80, 100));
    products.put(5,new product(5, "FRIES", 180, 100));
    products.put(6,new product(6, "JUICE", 50, 100));
    products.put(7,new product(7, "Wheat", 120, 10));

    customers.put("Lakshnya",new customer("Lakshnya", "laks"));
    customers.put("Dikshitha",new customer("Dikshitha", "diks"));
    customers.put("Divya",new customer("Divya", "divs"));
    customers.put("Ragavi",new customer("Ragavi", "mendali"));
    customers.put("Harsanya",new customer("Harsanya", "harsha"));
    customers.put("Afreen",new customer("Afreen", "afreen"));

}
static void adminmenu(){
    while (true) {
        System.out.println("1.Add products\n2.Modify products\n3.Delete Product\n4.View Products\n5.Reports\n6.Exit");
        int ch=sc.nextInt();
        sc.nextLine();
        switch(ch){
            case 1:
                System.out.println("Enter the id:");
                int pi=sc.nextInt();
                sc.nextLine();
                System.out.println("Enter the name:");
                String pn=sc.next();
                System.out.println("Enter the cost:");
                double pc=sc.nextDouble();
                System.out.println("Enter the quantity:");
                int pq=sc.nextInt();
                products.put(pi,new product(pi, pn, pc, pq));
                break;
            case 2:
                System.out.println("Enter the id:");
                int moid=sc.nextInt();
                if(products.containsKey(moid)){
                    System.out.println("Enter the new quantity:");
                    products.get(moid).quantity=sc.nextInt();
                }
                break;
            case 3:
                System.out.println("Enter the id:");
                int rid=sc.nextInt();
                products.remove(rid);
                break;
            case 4:
                for(product p:products.values()){
                    System.out.println("Product id:"+p.id+" Name:"+p.name+" Price:"+p.cost+" Quantity:"+p.quantity);
                }
                break;
            case 5:
                reports();
                break;
            case 6:
                return;
        }
        
    }
}
static void reports(){
    System.out.println("LOW STOCK");
    for(product p:products.values()){
        if(p.quantity<10){
            System.out.println(p.name);
        }
    }
    System.out.println("NEVER SOLD");
    for(product p:products.values()){
        if(p.sold==0){
            System.out.println(p.name);
        }
    }
    System.out.println("MOST SOLD:");
    for(product p:products.values()){
        if(p.sold>20){
            System.out.println(p.name);
        }
    }
}
static void customermenu(customer cus){
    while(true){
        System.out.println("1.View Products\n2.Add to cart\n3.View cart\n4.Delete Item\n5.Modify Quantity\n6.Checkout\n7.History\n8.Exit");
        int choice=sc.nextInt();
        switch(choice){
            case 1:
                for(product p:products.values()){
                    System.out.println("Product id:"+p.id+" Name:"+p.name+" Price:"+p.cost+" Quantity:"+p.quantity);
                }
                break;
            case 2:
                  System.out.println("Enter product id:");
                  int id=sc.nextInt();
                  if(!products.containsKey(id)){
                    System.out.println("Invalid");
                    break;
                  }
                  product p=products.get(id);
                  System.out.println("Enter the quantity:");
                  int qty=sc.nextInt();
                  if(qty>p.quantity){
                    System.out.println("Low Stock");
                    break;
                  }
                  boolean found=false;
                  for(cart ci:cus.cartItem){
                    if(ci.p.id==id){
                        ci.qty+=qty;
                        found=true;
                    }
                  }
                  if(!found){
                    cus.cartItem.add(new cart(p,qty));
                  }
                  System.out.println("Added to the cart");
                  break;
            case 3:
                double total=0;
                for(cart ci:cus.cartItem){
                    System.out.println(ci.p.name+"x"+ci.qty);
                    total+=ci.p.cost*ci.qty;
                }
                System.out.println("Total: "+total);
                break;
            case 4:
                System.out.println("Enter the product Id to be deleted:");
                int deid=sc.nextInt();
                Iterator<cart>it=cus.cartItem.iterator();
                boolean had=false;
                while(it.hasNext()){
                      cart ci=it.next();
                      if(ci.p.id==deid){
                        it.remove();
                        System.out.println("Item removed from cart");
                        had=true;
                        break;
                      }
                }
                if(!had){
                    System.out.println("Item not found in cart");
                }
                break;
            case 5:
                System.out.println("Enter product id to edit:");
                int eid=sc.nextInt();
                boolean found2=false;
                for(cart ci:cus.cartItem ){
                    if(ci.p.id==eid){
                        System.out.println("Enter the new Quantity:");
                        int nq=sc.nextInt();
                        if(nq>ci.p.quantity){
                            System.out.println("Not enough stock available");
                            break;
                        }
                    
                    if(nq ==0){
                         cus.cartItem.remove(ci);
                         System.out.println("Item removed from cart");
                    }
                    else{
                        ci.qty=nq;
                        System.out.println("Quantity updated");
                    }
                    found2=true;
                    break;
                }
            }
            if(!found2){
                System.out.println("Item not found in cart");
            }
            break;
            case 6:
                   double bill=0;
                   for(cart ci:cus.cartItem){
                    bill+=ci.p.cost*ci.qty;
                   }
                   if(bill>cus.credit){
                    System.out.println("Insufficient Credit");
                    break;
                   }
                   cus.points+=bill/100;
                   if(cus.points>=50){
                    bill-=100;
                    cus.points-=50;
                    System.out.println("100 discount aplied");
                   }
                   if(bill>5000){
                    cus.credit+=100;
                    System.out.println("100 cashback added");
                   }
                   cus.credit-=bill;
                   for(cart ci:cus.cartItem){
                     ci.p.quantity-=ci.qty;
                     ci.p.sold+=ci.qty;
                   }
                   cus.history.add("Bill: "+bill+" Date: "+LocalDate.now());
                   cus.cartItem.clear();
                   System.out.println("Payment Successful.Remaining credit:"+cus.credit+"Your points:"+cus.points);
                   break;
            case 7:
                for(String h:cus.history){
                    System.out.println(h);
                }
                break;
            case 8:
                return;
        }
    }
}

}
