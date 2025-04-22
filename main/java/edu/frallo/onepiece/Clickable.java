package edu.frallo.onepiece;


/**
 * Interface pour écouter les évènements sur le nom du diplome
 */

    public interface Clickable {
        void onClicItem(int itemIndex);
        void onRatingBarChange(int itemIndex, float value);
}
