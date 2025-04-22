package edu.frallo.onepiece;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CharacterAdapter extends BaseAdapter {
    private final List<OnepieceCharacter> characters;
    private List<OnepieceCharacter> filteredCharacters;
    private final Context context;
    private final LayoutInflater inflater;

    private int currentFilter = 1;

    private Clickable clickableActivity;

    private final String imagePrefix = "http://edu.info06.net/onepiece/pictures_ld/";

    public CharacterAdapter(Clickable activity, Context context, List<OnepieceCharacter> characters) {
        this.clickableActivity = activity;
        this.characters = characters;
        this.filteredCharacters = new ArrayList<>(this.characters);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.filteredCharacters.size();
    }

    @Override
    public Object getItem(int position) {
        return this.filteredCharacters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutItem = (LinearLayout) (convertView == null ? inflater.inflate(R.layout.character_layout, parent, false) : convertView);

        layoutItem.setOnClickListener(v -> clickableActivity.onClicItem(position));

        ImageView tvImageView = layoutItem.findViewById(R.id.listCharacterImage);
        Picasso.get().load(this.filteredCharacters.get(position).getPictureLowDefinition()).into(tvImageView);

        TextView tvName = layoutItem.findViewById(R.id.listCharacterName);
        tvName.setText(this.filteredCharacters.get(position).getName());

        RatingBar tvRatingBar = layoutItem.findViewById(R.id.listCharacterRating);
        tvRatingBar.setRating(this.filteredCharacters.get(position).getValue());

        TextView tvRatingBarText = layoutItem.findViewById(R.id.listCharacterRatingText);
        tvRatingBarText.setText(String.valueOf(this.filteredCharacters.get(position).getValue()));

        tvRatingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            int realPosition = this.characters.indexOf(this.filteredCharacters.get(position));

            clickableActivity.onRatingBarChange(realPosition, rating);
        });

        return layoutItem;
    }

    public void setCurrentFilter(int filter) {
        this.currentFilter = filter;

    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

        this.filteredCharacters = this.characters.stream()
                .filter(c -> c.getValue() > this.currentFilter)
                .collect(Collectors.toList());
    }
}
