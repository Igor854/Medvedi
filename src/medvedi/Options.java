package medvedi;

public class Options {
    public int numberOfBears;
    public boolean openMap;
    public boolean god;
    public boolean blind;
    public boolean search;
    public boolean searchThread;

    public Options() {
        numberOfBears = 2; // если больше 2 медведей, могут некорректно отображаться несколько медведей на одной клетке
        openMap = false;
        god = false;
        blind = false;
        search = false;
        searchThread = false;
    }
}
