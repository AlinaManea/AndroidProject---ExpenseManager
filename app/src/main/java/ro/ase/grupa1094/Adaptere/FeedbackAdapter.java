package ro.ase.grupa1094.Adaptere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import ro.ase.grupa1094.Clase.Feedback;
import ro.ase.grupa1094.R;

public class FeedbackAdapter extends ArrayAdapter<Feedback> {
    private Context context;
    private int layoutId;
    private List<Feedback> feedbackList;
    private LayoutInflater inflater;

    public FeedbackAdapter(@NonNull Context context, int resource, @NonNull List<Feedback> feedbackList) {
        super(context, resource, feedbackList);
        this.context = context;
        this.layoutId = resource;
        this.feedbackList = feedbackList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(layoutId, parent, false);
        Feedback feedback = feedbackList.get(position);

        TextView tvMesaj = view.findViewById(R.id.textFeedback);
        TextView tvData = view.findViewById(R.id.textData);
        TextView tvRating=view.findViewById(R.id.textRating);

        String formattedMesaj = "Mesaj: " + feedback.getMesaj();
        tvMesaj.setText(formattedMesaj);


        String formattedRating = "Rating: " + feedback.getRating();
        if (feedback.getRating() < 3) {
            tvRating.setText("Rating: " + feedback.getRating() + " :(");}
            else
        {
            tvRating.setText("Rating: " + feedback.getRating());
        }


        String formattedData = "Data: " + feedback.getData();
        tvData.setText(formattedData);



        return view;
    }
}
