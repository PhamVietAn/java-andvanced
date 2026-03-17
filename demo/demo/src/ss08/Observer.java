package ss08;

import java.util.ArrayList;
import java.util.List;

public class Observer {
    public static void main(String[] args) {
        ReceiverObserver r1 = new ConcreteReceiver("Người nhận 1");
        ReceiverObserver r2 = new ConcreteReceiver("Người nhận 2");

        SourceNews VNExpress = new SourceNews();
        VNExpress.addObserver(r1);
        VNExpress.addObserver(r2);
        VNExpress.sendNews();
    }
}

// người nhận là ai
interface ReceiverObserver {
    void update(String news);

}

// nguồn tin tức
class SourceNews {
    private List<ReceiverObserver> observers = new ArrayList<>();
    public void addObserver(ReceiverObserver receiver){
        observers.add(receiver);
    }

    public void sendNews(){
        String content = "Tin tức mới nhất";
        observers.forEach(observer -> observer.update(content));
    }
}

class ConcreteReceiver implements ReceiverObserver {
    private String name;
    public ConcreteReceiver(String name) {
        this.name = name;
    }

    @Override
    public void update(String news) {
        System.out.println(name + " nhận được tin: " + news);
    }
}
