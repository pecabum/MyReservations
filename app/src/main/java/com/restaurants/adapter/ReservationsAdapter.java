package com.restaurants.adapter;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.restaurants.dao.Reservation;
import com.restaurants.databinding.LayoutReservationItemBinding;
import com.restaurants.interfaces.ReservationsInteractionListener;

import java.util.List;

public class ReservationsAdapter extends RecyclerView.Adapter<ReservationsAdapter.ReservationViewHolder> {

    private ReservationsInteractionListener interactionListener;
    private List<Reservation> reservationsList;

    public ReservationsAdapter(ReservationsInteractionListener interactionListener) {
        this.interactionListener = interactionListener;
    }

    public void setReservationsList(final List<Reservation> updatedReservations) {
        if (this.reservationsList == null) {
            this.reservationsList = updatedReservations;
            notifyItemRangeInserted(0, reservationsList.size());
        } else {

            // Comparing the objects and their content to be able to put the new one to the right place
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return reservationsList.size();
                }

                @Override
                public int getNewListSize() {
                    return updatedReservations.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return reservationsList.get(oldItemPosition).getUid() ==
                            updatedReservations.get(newItemPosition).getUid();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Reservation reservation = updatedReservations.get(newItemPosition);
                    Reservation old = reservationsList.get(oldItemPosition);
                    return reservation.getUid() == old.getUid();
                }
            });
            this.reservationsList = updatedReservations;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public ReservationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutReservationItemBinding binding = LayoutReservationItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ReservationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ReservationViewHolder holder, int position) {
        holder.binding.setReservation(reservationsList.get(position));
        holder.binding.setListener(interactionListener);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return reservationsList == null ? 0 : reservationsList.size();
    }

    static class ReservationViewHolder extends RecyclerView.ViewHolder {

        final LayoutReservationItemBinding binding;

        ReservationViewHolder(LayoutReservationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}