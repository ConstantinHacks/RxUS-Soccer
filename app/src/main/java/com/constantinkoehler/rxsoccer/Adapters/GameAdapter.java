package com.constantinkoehler.rxsoccer.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.constantinkoehler.rxsoccer.R;
import com.constantinkoehler.rxsoccer.models.Game;

import java.util.ArrayList;
import java.util.List;

public class GameAdapter extends BaseAdapter {

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

    private static class GameViewHolder {
        private TextView usTeamNameTV;
        private TextView opponentTeamNameTV;
        private TextView resultTV;

        public GameViewHolder(View view){
            usTeamNameTV = view.findViewById(R.id.usTeamTV);
            opponentTeamNameTV = view.findViewById(R.id.opponentTV);
            resultTV = view.findViewById(R.id.resultTV);
        }

        public void setGameInfo(Game game){
            usTeamNameTV.setText(game.getUsTeam());
            opponentTeamNameTV.setText(game.getOpponentTeam());
            String usScore = game.getResult().length != 0 ? String.valueOf(game.getResult()[0]) : "";
            String oppScore = game.getResult().length != 0 ? String.valueOf(game.getResult()[1]) : "";

            String result = String.format("%s - %s",usScore,oppScore);
            resultTV.setText(result);
        }

    }
}
