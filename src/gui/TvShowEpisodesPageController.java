package gui;

import domain.MyDate;
import domain.entity.Episode;
import domain.item.TvShow;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class TvShowEpisodesPageController extends ListPageController<TvShow, Episode> {

    private final int season;
    
    //<editor-fold defaultstate="collapsed" desc="constructors">
    public TvShowEpisodesPageController(TvShow tvShow, int season) {
        super(tvShow, false);
        this.season = season;
        init();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="methods">
    @Override
    protected String getTitle() {
        return String.format("%s Season %d: Episodes", entity.getTitle(), season);
    }

    @Override
    protected List<Episode> getList() {
        return entity.getEpisodes(season);
    }

    @Override
    protected TableColumn<Episode, Object>[] getTableColumns() {
        TableColumn<Episode, Integer> episode = new TableColumn<>("Episode");
        episode.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEpisodeNr()));
        TableColumn<Episode, String> title = new TableColumn<>("Title");
        title.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        TableColumn<Episode, String> releaseDate = new TableColumn<>("ReleaseDate");
        releaseDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReleaseDate().toString()));
        TableColumn<Episode, CheckBox> watched = new TableColumn<>("Watched");
        watched.setCellValueFactory((TableColumn.CellDataFeatures<Episode, CheckBox> param) -> {
            Episode ep = param.getValue();
            CheckBox chbIsWatched = new CheckBox();
            chbIsWatched.selectedProperty().setValue(ep.isWatched());
            chbIsWatched.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                ep.setWatched(newValue);
                controller.editEntity(ep);
            });
            return new SimpleObjectProperty<>(chbIsWatched);
        });
        TableColumn<Episode, CheckBox> collected = new TableColumn<>("Collected");
        collected.setCellValueFactory((TableColumn.CellDataFeatures<Episode, CheckBox> param) -> {
            Episode ep = param.getValue();
            CheckBox chbIsCollected = new CheckBox();
            chbIsCollected.selectedProperty().setValue(ep.isInCollection());
            chbIsCollected.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                ep.setInCollection(newValue);
                controller.editEntity(ep);
            });
            return new SimpleObjectProperty<>(chbIsCollected);
        });
        return new TableColumn[]{episode, title, releaseDate, watched, collected};
    }

    @Override
    protected void setGridControls() {
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
    protected void showDetails(Episode object) {
        controller.addToScenePath(new EntityDetailsPageController(object));
    }

    @Override
    protected void save() {
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
    protected void deleteObject(Episode object) {

        entity.removeEpisodes(object);
        controller.deleteEntity(object);

    }

    //</editor-fold>
    
}
