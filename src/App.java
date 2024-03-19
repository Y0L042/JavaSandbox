class App {
    static App app;

    public static void main(String[] args) {
        app = new App();
        app.run(args);
    }

    void run(String[] args) {
        System.out.println("Hello, World!");
    }
}