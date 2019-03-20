package com.constantinkoehler.rxsoccer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.constantinkoehler.rxsoccer.R;
import com.constantinkoehler.rxsoccer.models.Game;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GameAdapter extends BaseAdapter implements Filterable {

    private List<Game> allGames = new ArrayList<>();
    private List<Game> games = new ArrayList<>();

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Game getItem(int position) {
        return games.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = (convertView != null ? convertView : createView(parent));
        final GameViewHolder viewHolder = (GameViewHolder) view.getTag();
        viewHolder.setGameInfo(getItem(position));
        return view;
    }

    private View createView(ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.game_row, parent, false);
        final GameViewHolder viewHolder = new GameViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    public void setGames(List<Game> gameList){
        games.clear();
        games.addAll(gameList);
        notifyDataSetChanged();
    }

    public void setAllGames(List<Game> gameList){
        allGames.addAll(gameList);
        setGames(gameList);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                ArrayList<Game> FilteredArrayGames = new ArrayList<Game>();
                String constraintStr = constraint.toString().toLowerCase();
                for (Game game: allGames) {
                    if(constraintStr.equalsIgnoreCase("all") || game.getUsTeam().equalsIgnoreCase(constraintStr)){
                        FilteredArrayGames.add(game);
                    }
                }

                filterResults.count = FilteredArrayGames.size();
                filterResults.values = FilteredArrayGames;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                games = (List<Game>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    private static class GameViewHolder {
        private TextView usTeamNameTV;
        private TextView opponentTeamNameTV;
        private TextView resultTV;
        private ImageView opponentFlagIV;
        private Context context;

        public GameViewHolder(View view){
            usTeamNameTV = view.findViewById(R.id.usTeamTV);
            opponentTeamNameTV = view.findViewById(R.id.opponentTV);
            resultTV = view.findViewById(R.id.resultTV);
            opponentFlagIV = view.findViewById(R.id.opponentIV);
            context = view.getContext();
        }

        public void setGameInfo(Game game){
            usTeamNameTV.setText(game.getUsTeam());
            opponentTeamNameTV.setText(game.getOpponentTeam());
            Picasso.get().load(game.flagResource(context)).into(opponentFlagIV);
            resultTV.setText(game.getScoreLine());
        }

    }
}
