package tpv.cirer.com.restaurante.modelo;

/**
 * Created by JUAN on 28/12/2016.
 */

public class Terminal {
    private String TerminalUserName;
    private String TerminalTerminal;
    private String TerminalPrinter;
    private String TerminalDeviceType;
    private String TerminalPrintIp;
    
    private int TerminalId;
    private int TerminalPrintLanguage;
    private int TerminalPrintInterval;
    
    public Terminal(){}

    public Terminal(String printer, String terminal, String devicetype, String printip, int printlanguage, int printinterval ){
        this.TerminalPrinter = printer;
        this.TerminalTerminal = terminal;
        this.TerminalDeviceType = devicetype;
        this.TerminalPrintIp = printip;
        this.TerminalPrintLanguage = printlanguage;
        this.TerminalPrintInterval = printinterval;
    }
    public String getTerminalUserName() {
        return TerminalUserName;
    }
    public void setTerminalUserName(String terminalUserName) {
        this.TerminalUserName = terminalUserName;
    }

    public String getTerminalTerminal() {
        return TerminalTerminal;
    }
    public void setTerminalTerminal(String terminalTerminal) {
        this.TerminalTerminal = terminalTerminal;
    }

    public String getTerminalPrinter() {
        return TerminalPrinter;
    }
    public void setTerminalPrinter(String terminalPrinter) {
        this.TerminalPrinter = terminalPrinter;
    }

    public String getTerminalDeviceType() {
        return TerminalDeviceType;
    }
    public void setTerminalDeviceType(String terminalDeviceType) {
        this.TerminalDeviceType = terminalDeviceType;
    }

    public String getTerminalPrintIp() {
        return TerminalPrintIp;
    }
    public void setTerminalPrintIp(String terminalPrintIp) {
        this.TerminalPrintIp = terminalPrintIp;
    }

    public int getTerminalId() {
        return TerminalId;
    }
    public void setTerminalId(int terminalId) {
        this.TerminalId = terminalId;
    }


    public int getTerminalPrintLanguage() {
        return TerminalPrintLanguage;
    }
    public void setTerminalPrintLanguage(int terminalPrintLanguage) {
        this.TerminalPrintLanguage = terminalPrintLanguage;
    }

    public int getTerminalPrintInterval() {
        return TerminalPrintInterval;
    }
    public void setTerminalPrintInterval(int terminalPrintInterval) {
        this.TerminalPrintInterval = terminalPrintInterval;
    }

}
