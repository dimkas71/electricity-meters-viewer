package compsevice.ua.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import compsevice.ua.app.R;
import compsevice.ua.app.model.ContractInfo;
import compsevice.ua.app.model.Counter;
import compsevice.ua.app.model.ServiceType;

public class ContractInfoAdapter extends RecyclerView.Adapter<ContractInfoAdapter.ViewHolder> implements Filterable {


    private List<ContractInfo> contracts;
    private Context context;

    public ContractInfoAdapter(Context context, List<ContractInfo> contracts) {
        this.contracts = contracts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder vh = new ViewHolder(inflater.inflate(R.layout.list_item, parent, false));
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ContractInfo contractInfo = contracts.get(position);
        holder.tvSectorNumber.setText(context.getResources().getString(R.string.text_sector_number, contractInfo.getSectorNumber()));
        holder.tvContractNumber.setText(context.getResources().getString(R.string.text_contract_number,
                                            contractInfo.getNumber()));

        holder.tvOwner.setText(contractInfo.getOwner());

        StringBuilder sb = new StringBuilder();

        for (Counter c : contractInfo.getCounters()) {

            sb.append(context.getResources().getString(R.string.text_counters_factory_number))
                    .append(c.getFactoryNumber())
                    .append("    ")
                    .append(String.format("%s %d", context.getResources().getString(R.string.text_counters_value), c.getValue()))
                    .append("\n");
        }

        sb.deleteCharAt(sb.toString().length() - 1);


        holder.tvCounters.setText(sb.toString());

        double creditElectricity = contractInfo.creditByService(ServiceType.Electricity);

        holder.tvCreditElectricity.setTextColor(context.getResources().getColor(R.color.colorCredit));


        if (creditElectricity > 0.0d) {
            holder.tvCreditElectricity.setTextColor(context.getResources().getColor(R.color.colorCreditRed));
        }

        holder.tvCreditElectricity.setText(String.format("%.2f", creditElectricity));

        double creditVideo = contractInfo.creditByService(ServiceType.Video);
        holder.tvCreditVideo.setTextColor(context.getResources().getColor(R.color.colorCredit));

        if (creditVideo > 0.0d) {
            holder.tvCreditVideo.setTextColor(context.getResources().getColor(R.color.colorCreditRed));
        }

        holder.tvCreditVideo.setText(String.format("%.2f", creditVideo));

        double creditService = contractInfo.creditByService(ServiceType.Service);
        holder.tvCreditService.setTextColor(context.getResources().getColor(R.color.colorCredit));

        if (creditService > 0.0d) {
            holder.tvCreditService.setTextColor(context.getResources().getColor(R.color.colorCreditRed));
        }

        holder.tvCreditService.setText(String.format("%.2f", creditService));


    }

    @Override
    public int getItemCount() {
        return contracts.size();
    }

    @Override
    public Filter getFilter() {
        return new ContractInfoFilter(this, this.contracts);
    }

    public void update(List<ContractInfo> updated) {
        this.contracts = updated;
        notifyDataSetChanged();
    }

    public ContractInfo getDataAt(int position) {
        return contracts.get(position);
    }



    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSectorNumber;
        TextView tvContractNumber;
        TextView tvOwner;
        TextView tvCounters;
        TextView tvCreditElectricity;
        TextView tvCreditVideo;
        TextView tvCreditService;

        public ViewHolder(View parent) {
            super(parent);
            tvSectorNumber = parent.findViewById(R.id.text_sector_number);
            tvContractNumber = parent.findViewById(R.id.text_number);
            tvOwner = parent.findViewById(R.id.text_owner);
            tvCounters = parent.findViewById(R.id.text_counters);
            tvCreditElectricity = parent.findViewById(R.id.text_credit_electricity);
            tvCreditVideo = parent.findViewById(R.id.text_credit_video);
            tvCreditService = parent.findViewById(R.id.text_credit_serivice);
        }
    }

    public static class ContractInfoFilter extends Filter {

        private static final String TAG = ContractInfoFilter.class.getSimpleName();

        List<ContractInfo> originalInfos = new ArrayList<>();

        WeakReference<ContractInfoAdapter> adapter;

        ContractInfoFilter(ContractInfoAdapter adapter, List<ContractInfo> newInfos) {
            this.originalInfos = newInfos;
            this.adapter = new WeakReference<>(adapter);

        }


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.i(TAG, "Filtering on a constraint " + constraint);
            FilterResults results = new FilterResults();

            if (constraint == null) {
                results.values = new ArrayList<>(this.originalInfos);
                results.count = this.originalInfos.size();
            } else {

                List<ContractInfo> filteredInfos = new ArrayList<>();

                for (ContractInfo ci : originalInfos) {
                    if (ci.matchesQuery(constraint)) {
                        filteredInfos.add(ci);
                    }
                }

                results.values = new ArrayList<>(filteredInfos);
                results.count = filteredInfos.size();

            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            Log.i(TAG, "Publishing results on the Main thread. All count is: " + results.count);
            adapter.get().update((List<ContractInfo>) results.values);
        }


        public void update(List<ContractInfo> newInfos) {
            this.originalInfos = new ArrayList<>(newInfos);
        }

    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private final static String TAG = RecyclerTouchListener.class.getSimpleName();

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            this.gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());

                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });

        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null) {
                if (e.getAction() == MotionEvent.ACTION_UP) {
                    clickListener.onClick(child, rv.getChildAdapterPosition(child));
                }
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {}

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}


        public interface ClickListener {

            void onClick(View v, int position);

            void onLongClick(View v, int position);
        }
    }


}
