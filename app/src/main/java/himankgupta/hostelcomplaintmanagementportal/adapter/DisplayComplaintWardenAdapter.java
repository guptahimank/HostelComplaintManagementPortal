package himankgupta.hostelcomplaintmanagementportal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import himankgupta.hostelcomplaintmanagementportal.R;
import himankgupta.hostelcomplaintmanagementportal.modal.DisplayComplaintWardenModal;

/**
 * Created by Hp on 11/18/2017.
 */

public class DisplayComplaintWardenAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    List<DisplayComplaintWardenModal> list = new ArrayList<>();

    String roll_no;
    String complaint_no;


    public DisplayComplaintWardenAdapter(Context context, List<DisplayComplaintWardenModal> list) {
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder {

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
        TextView textViewComplaintID, textViewStatus, textViewDescription, textViewComplaintType, textViewComplaintDate;
        Spinner spinnerComplaintStatus;
        DisplayComplaintWardenModal displayComplaintWardenModal;

        if (convertView == null)


        {
            convertView = inflater.inflate(R.layout.inner_list_display_complaint_warden, null, false);


            textViewComplaintID = (TextView) convertView.findViewById(R.id.textViewWardenComplaintID);
            textViewStatus = (TextView) convertView.findViewById(R.id.textViewWardenComplaintStatus);
            textViewDescription = (TextView) convertView.findViewById(R.id.textViewWardenComplaintDescription);
            textViewComplaintType = (TextView) convertView.findViewById(R.id.textViewWardenComplaintType);
            textViewComplaintDate = (TextView) convertView.findViewById(R.id.textViewWardenComplaintDate);

            spinnerComplaintStatus = (Spinner) convertView.findViewById(R.id.spinnerWardenComplaintStatus);

            displayComplaintWardenModal = new DisplayComplaintWardenModal(context);
            spinnerComplaintStatus.setAdapter(displayComplaintWardenModal.getAdapter());
            textViewComplaintID.setText(list.get(position).getComplaint_no());
            if (list.get(position).getStatus().equals("1")) {
                textViewStatus.setText("Initiated");
            } else if (list.get(position).getStatus().equals("2")) {
                textViewStatus.setText("Activated");
            } else if (list.get(position).getStatus().equals("3")) {
                textViewStatus.setText("Finished");
            }
            textViewDescription.setText(list.get(position).getDescription());
            textViewComplaintType.setText(list.get(position).getType());
            textViewComplaintDate.setText(list.get(position).getDate());
        }


        return convertView;
    }


}
