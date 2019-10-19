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
import himankgupta.hostelcomplaintmanagementportal.modal.DisplayComplaintCaretakerModal;


public class DisplayComplaintCaretakerAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    List<DisplayComplaintCaretakerModal> list = new ArrayList<>();


    String roll_no;
    String complaint_no;


    public DisplayComplaintCaretakerAdapter(Context context, List<DisplayComplaintCaretakerModal> list) {
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

        TextView textViewComplaintID2, textViewStatus2, textViewDescription2, textViewComplaintType2, textViewComplaintDate2;
        Spinner spinnerComplaintStatus2;
        DisplayComplaintCaretakerModal displayComplaintCaretakerModal;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.inner_list_display_complaint_caretaker, null, false);


            textViewComplaintID2 = (TextView) convertView.findViewById(R.id.textViewComplaintID2);
            textViewStatus2 = (TextView) convertView.findViewById(R.id.textViewComplaintStatus2);
            textViewDescription2 = (TextView) convertView.findViewById(R.id.textViewComplaintDescription2);
            textViewComplaintType2 = (TextView) convertView.findViewById(R.id.textViewComplaintType2);
            textViewComplaintDate2 = (TextView) convertView.findViewById(R.id.textViewComplaintDate2);

            spinnerComplaintStatus2 = (Spinner) convertView.findViewById(R.id.spinnerComplaintStatus2);

            displayComplaintCaretakerModal = new DisplayComplaintCaretakerModal(context);
            spinnerComplaintStatus2.setAdapter(displayComplaintCaretakerModal.getAdapter());
            textViewComplaintID2.setText(list.get(position).getComplaint_no());
            if (list.get(position).getStatus().equals("1")) {
                textViewStatus2.setText("Initiated");
            } else if (list.get(position).getStatus().equals("2")) {
                textViewStatus2.setText("Activated");
            } else if (list.get(position).getStatus().equals("3")) {
                textViewStatus2.setText("Finished");
            }
            textViewDescription2.setText(list.get(position).getDescription());
            textViewComplaintType2.setText(list.get(position).getType());
            textViewComplaintDate2.setText(list.get(position).getDate());


        }


        return convertView;
    }


}
