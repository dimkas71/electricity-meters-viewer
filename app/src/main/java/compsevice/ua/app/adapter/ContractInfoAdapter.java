package compsevice.ua.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import compsevice.ua.app.R;
import compsevice.ua.app.model.ContractInfo;

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

    }

    @Override
    public int getItemCount() {
        return contracts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSectorNumber;
        TextView tvContractNumber;
        TextView tvOwner;

        public ViewHolder(View parent) {
            super(parent);
            tvSectorNumber = parent.findViewById(R.id.text_sector_number);
            tvContractNumber = parent.findViewById(R.id.text_number);
            tvOwner = parent.findViewById(R.id.text_owner);
        }
    }


}
