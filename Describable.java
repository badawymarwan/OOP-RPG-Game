public interface Describable {

    // this interface forces any class that implements it to provide a method that shows name,hp, stats etc
    // so when warrior,mage and rogue implement this interface, they are forced to override getstatusreport to show their info
    // we are using this to allow classes to showcase their info
    String getStatusReport();
    // it is a string method because we may want to add anything before or after it, we dont want to restrict ourselves to a certain statement
  

}
