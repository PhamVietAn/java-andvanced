package ss08;

public class FactoryMethod {
    public static Shape create(String type) {
        switch (type.toLowerCase()) {
            case "circle":
                return new Circle();
            case "rectangle":
                return new Rectangle();
            default:
                throw new IllegalArgumentException("Unknown shape type: " + type);
        }
    }

    public static void  main(String[] args){
        Shape shape = FactoryMethod.create("circle");
        shape.draw();
    }
}

interface Shape {
    void draw();
}

class Circle implements Shape {
    @Override
    public void draw() {
        // vẽ hình tròn
    }
}

class  Rectangle implements Shape {
    @Override
    public void draw() {
        // vẽ hình chữ nhật
    }
}