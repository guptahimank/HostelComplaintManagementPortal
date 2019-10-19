package himankgupta.hostelcomplaintmanagementportal.modal;

import android.content.Context;
import android.widget.ArrayAdapter;

import himankgupta.hostelcomplaintmanagementportal.R;

public class DisplayComplaintWardenModal {
    private int selected;
    private ArrayAdapter<CharSequence> adapter;
    String complaint_no,status,description,type,date;

    public DisplayComplaintWardenModal(Context parent) {
        adapter = ArrayAdapter.createFromResource(parent, R.array.Change, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }


    public String getComplaint_no() {return complaint_no;}

    public void setComplaint_no(String complaint_no) {this.complaint_no = complaint_no;}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {this.description = description;}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayAdapter<CharSequence> getAdapter() {
        return adapter;
    }

    public String getText() {
        return (String) adapter.getItem(selected);
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}

