class App {
    static App app;

    public static void main(String[] args) {
        app = new App();
        app.run(args);
    }

    void run(String[] args) {
        Trees_Study.main(args);
    }
}