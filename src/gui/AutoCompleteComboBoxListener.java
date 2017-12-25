package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Helper class for ComboBox that provides searching
 * when it has too many items
 * @param <T> 
 */
public class AutoCompleteComboBoxListener<T> implements EventHandler<KeyEvent>{

    private final ComboBox comboBox;
    private final StringBuilder sb;
    private final ObservableList<T> data;
    private boolean moveCaretToPos = false;
    private int caretPos;

    public AutoCompleteComboBoxListener(ComboBox comboBox) {
        this.comboBox = comboBox;
        sb = new StringBuilder();
        data = comboBox.getItems();

        this.comboBox.setEditable(true);
        this.comboBox.setOnKeyPressed(event -> {comboBox.hide();});
        this.comboBox.setOnKeyReleased(AutoCompleteComboBoxListener.this);
    }

    @Override
    public void handle(KeyEvent event) {

        if (event.getCode() != null) {
            switch (event.getCode()) {
                case UP: case DOWN:
                    if (!comboBox.isShowing()) comboBox.show();
                    caretPos = -1;
                    moveCaret(comboBox.getEditor().getText().length());
                    return;
                case BACK_SPACE: case DELETE:
                    moveCaretToPos = true;
                    caretPos = comboBox.getEditor().getCaretPosition();
                    break;
            }
        }

        if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
                || event.isControlDown() || event.getCode() == KeyCode.HOME
                || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
            return;
        }

        ObservableList list = FXCollections.observableArrayList();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).toString().toLowerCase().startsWith(comboBox.getEditor().getText().toLowerCase())) {
                list.add(data.get(i));
            }
        }
        String t = comboBox.getEditor().getText();

        comboBox.setItems(list);
        comboBox.getEditor().setText(t);
        if (!moveCaretToPos) caretPos = -1;
        moveCaret(t.length());
        if (!list.isEmpty()) comboBox.show();
        
    }

    private void moveCaret(int textLength) {
        if (caretPos == -1) comboBox.getEditor().positionCaret(textLength);
        else comboBox.getEditor().positionCaret(caretPos);
        moveCaretToPos = false;
    }

}
