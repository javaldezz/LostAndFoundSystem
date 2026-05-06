package app.dto;


public class DashboardSummaryDTO {
    
    private long locations;
    private long items;
    private long lostReports;
    private long foundReports;
    private long students;

    public DashboardSummaryDTO() {}
    
    public long getLocations() { return locations; }
    public void setLocations(long locations) { this.locations = locations; }

    public long getItems() { return items; }
    public void setItems(long items) { this.items = items; }

    public long getLostReports() { return lostReports; }
    public void setLostReports(long lostReports) { this.lostReports = lostReports; }

    public long getFoundReports() { return foundReports; }
    public void setFoundReports(long foundReports) { this.foundReports = foundReports; }

    public long getStudents() { return students; }
    public void setStudents(long students) { this.students = students; }
}