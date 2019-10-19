package himankgupta.hostelcomplaintmanagementportal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import himankgupta.hostelcomplaintmanagementportal.R;
import himankgupta.hostelcomplaintmanagementportal.modal.DisplayComplaintModal;

public class DisplayComplaintStudentAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    List<DisplayComplaintModal> list = new ArrayList<>();


    public DisplayComplaintStudentAdapter(Context context, List<DisplayComplaintModal> list)
    {
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        convertView = inflater.inflate(R.layout.inner_list_display_complaint_student,null,false);
        TextView textViewComplaintID,textViewStatus,textViewDescription,textViewComplaintType, textViewComplaintDate;
        textViewComplaintID = (TextView)convertView.findViewById(R.id.textViewComplaintID);
        textViewStatus = (TextView)convertView.findViewById(R.id.textViewComplaintStatus);
        textViewDescription = (TextView)convertView.findViewById(R.id.textViewComplaintDescription);
        textViewComplaintType = (TextView)convertView.findViewById(R.id.textViewComplaintType);
        textViewComplaintDate =(TextView)convertView.findViewById(R.id.textViewComplaintDate);

        textViewComplaintID.setText(list.get(position).getComplaint_no());

        if(list.get(position).getStatus().equals("1"))
        { textViewStatus.setText("Initiated");}
        else if(list.get(position).getStatus().equals("2"))
        {
            textViewStatus.setText("Activated");
        }
        else if(list.get(position).getStatus().equals("3"))
        {
            textViewStatus.setText("Finished");
        }
        textViewDescription.setText(list.get(position).getDescription());
        textViewComplaintType.setText(list.get(position).getType());
        textViewComplaintDate.setText(list.get(position).getDate());
        return convertView;
    }
}
