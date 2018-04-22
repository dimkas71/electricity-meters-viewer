package compsevice.ua.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import compsevice.ua.app.R;
import compsevice.ua.app.model.ContractInfo;
import compsevice.ua.app.model.Counter;

public class ContractInfoAdapter extends RecyclerView.Adapter<ContractInfoAdapter.ViewHolder> {


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

        double creditElectricity = contractInfo.creditByService(ContractInfo.ELECTRICITY);

        holder.tvCreditElectricity.setTextColor(context.getResources().getColor(R.color.colorCredit));


        if (creditElectricity > 0.0d) {
            holder.tvCreditElectricity.setTextColor(context.getResources().getColor(R.color.colorCreditRed));
        }

        holder.tvCreditElectricity.setText(String.format("%.2f", creditElectricity));

        double creditVideo = contractInfo.creditByService(ContractInfo.VIDEO);
        holder.tvCreditVideo.setTextColor(context.getResources().getColor(R.color.colorCredit));

        if (creditVideo > 0.0d) {
            holder.tvCreditVideo.setTextColor(context.getResources().getColor(R.color.colorCreditRed));
        }

        holder.tvCreditVideo.setText(String.format("%.2f", creditVideo));

        double creditService = contractInfo.creditByService(ContractInfo.SERVICE);
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


}
