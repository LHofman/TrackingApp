package gui;

import domain.MyDate;
import domain.entity.Episode;
import domain.item.TvShow;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class TvShowSeasonsPageController extends ListPageController<TvShow, Integer> {

    //<editor-fold defaultstate="collapsed" desc="constructors">
    public TvShowSeasonsPageController(TvShow tvShow) {
        super(tvShow, false);
        init();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="methods">
    @Override
    protected String getTitle() {
        return entity.getTitle().concat(": Seasons");
    }

    @Override
    protected List<Integer> getList() {
        return entity.getSeasons();
    }

    @Override
    protected TableColumn<Integer, Object>[] getTableColumns() {
        TableColumn<Integer, Integer> season = new TableColumn<>("Season");
        season.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
        TableColumn<Integer, String> releaseDate = new TableColumn<>("ReleaseDate");
        releaseDate.setCellValueFactory(cellData -> new SimpleStringProperty(entity.getEpisodes(cellData.getValue()).get(0).getReleaseDate().toString()));
        TableColumn<Integer, CheckBox> watched = new TableColumn<>("Watched");
        watched.setCellValueFactory((TableColumn.CellDataFeatures<Integer, CheckBox> param) -> {
            int selectedSeason = param.getValue();
            CheckBox chbIsWatched = new CheckBox();
            chbIsWatched.selectedProperty().setValue(entity.isWatched(selectedSeason));
            chbIsWatched.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                for (Episode episode: entity.getEpisodes(selectedSeason)){
                    episode.setWatched(newValue);
                    controller.editEntity(episode);
                }
            });
            return new SimpleObjectProperty<>(chbIsWatched);
        });
        TableColumn<Integer, CheckBox> collected = new TableColumn<>("Collected");
        collected.setCellValueFactory((TableColumn.CellDataFeatures<Integer, CheckBox> param) -> {
            int selectedSeason = param.getValue();
            CheckBox chbIsCollected = new CheckBox();
            chbIsCollected.selectedProperty().setValue(entity.isCollected(selectedSeason));
            chbIsCollected.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                for (Episode episode: entity.getEpisodes(selectedSeason)){
                    episode.setInCollection(newValue);
                    controller.editEntity(episode);
                }
            });
            
            return new SimpleObjectProperty<>(chbIsCollected);
        });
        return new TableColumn[]{season, releaseDate, watched, collected};
    }

    @Override
    protected void setGridControls() {
        grid.addNode("lblSeason", "Label", "Season: ");
        grid.addNode("txtSeason", "TextField");
        grid.addRow("lblSeason", "txtSeason");

        grid.addNode("lblEpisodeNr", "Label", "Episode Nr: ");
        grid.addNode("txtEpisodeNr", "TextField");
        grid.addRow("lblEpisodeNr", "txtEpisodeNr");

        grid.addNode("lblTitle", "Label", "Title: ");
        grid.addNode("txtTitle", "TextField");
        grid.addRow("lblTitle", "txtTitle");

        grid.addNode("lblReleaseDate", "Label", "ReleaseDate: ");
        grid.addNode("dpReleaseDate", "DatePicker");
        grid.addRow("lblReleaseDate", "dpReleaseDate");

        grid.addNode("lblWatched", "Label", "Watched: ");
        grid.addNode("chbWatched", "CheckBox");
        grid.addRow("lblWatched", "chbWatched");

        grid.addNode("lblCollected", "Label", "Collected: ");
        grid.addNode("chbCollected", "CheckBox");
        grid.addRow("lblCollected", "chbCollected");
    }

    @Override
    protected void showDetails(Integer object) {
        controller.addToScenePath(new TvShowEpisodesPageController(entity, object));
    }

    @Override
    protected void save() {
        int season = Integer.parseInt(((TextField) grid.getNode("txtSeason")).getText());
        int episodeNr = Integer.parseInt(((TextField) grid.getNode("txtEpisodeNr")).getText());
        String title = ((TextField) grid.getNode("txtTitle")).getText();
        MyDate releaseDate = new MyDate(((DatePicker) grid.getNode("dpReleaseDate")).getValue());
        boolean watched = ((CheckBox) grid.getNode("chbWatched")).isSelected();
        boolean collected = ((CheckBox) grid.getNode("chbCollected")).isSelected();

        Episode episode = new Episode(season, episodeNr, title, releaseDate, watched, collected);
        entity.addEpisodes(episode);

        grid.clearFields();
    }

    @Override
    protected String getTitleSelected(Integer object){
        return entity.toString().concat(" Season " + object);
    }
    
    @Override
    protected void deleteObject(Integer object) {

        List<Episode> episodes = entity.getEpisodes(object);
        entity.removeEpisodes(episodes.toArray(new Episode[episodes.size()]));
        episodes.forEach((episode) -> controller.deleteEntity(episode));

    }

    //</editor-fold>
    
}
