package com.bosonit.project.lotrjfx.controller;

import com.bosonit.project.lotrjfx.model.BattleEngine;
import com.bosonit.project.lotrjfx.model.exceptions.InvalidInput;
import com.bosonit.project.lotrjfx.model.characters.beasts.Beast;
import com.bosonit.project.lotrjfx.model.characters.beasts.BeastClass;
import com.bosonit.project.lotrjfx.model.characters.heroes.Hero;
import com.bosonit.project.lotrjfx.model.characters.heroes.HeroClass;
import com.bosonit.project.lotrjfx.model.events.*;
import com.bosonit.project.lotrjfx.model.services.CharacterFactory;
import com.bosonit.project.lotrjfx.model.services.CharacterService;
import com.bosonit.project.lotrjfx.model.services.CharacterServiceImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class AppController implements Initializable {

    private final Random random = new Random();
    private final CharacterService characterService = new CharacterServiceImpl();

    private ObservableList<Hero> heroes = FXCollections.observableArrayList();
    private ObservableList<Beast> beasts = FXCollections.observableArrayList();

    @FXML private TextArea battleLog;
    @FXML private Button fightButton;

    //Heroes
    @FXML private TextField heroNameField;
    @FXML private TextField heroHealth;
    @FXML private TextField heroArmor;
    @FXML private ComboBox<String> heroClass;
    @FXML private ListView<Hero> heroList;

    //Beast
    @FXML private TextField beastName;
    @FXML private TextField beastHealth;
    @FXML private TextField beastArmor;
    @FXML private ComboBox<String> beastClass;
    @FXML private ListView<Beast> beastList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        heroList.setItems(heroes);
        beastList.setItems(beasts);

        heroClass.getItems().setAll(
                Arrays.stream(HeroClass.values()).map(HeroClass::toString).toList()
        );
        beastClass.getItems().setAll(
                Arrays.stream(BeastClass.values()).map(BeastClass::toString).toList()
        );
    }

    @FXML
    protected void onAddHeroButtonClick() {
        try {
            Hero hero = characterService.createHero(
                    heroClass.getValue(),
                    heroNameField.getText(),
                    heroHealth.getText(),
                    heroArmor.getText()
            );
            heroes.add(hero);

            cleanTextInput(heroNameField, heroHealth, heroArmor);
            heroClass.setValue("");
        } catch (InvalidInput e) {
            showErrorDialog(e.getMessage());
        }
    }

    @FXML
    protected void onRandomHeroButtonClick() {
        Hero randomHero = CharacterFactory.createRandomHero();

        heroNameField.setText(randomHero.getName());
        heroClass.setValue(randomHero.getHeroClass().toString());
        heroHealth.setText(Integer.toString(randomHero.getHealth()));
        heroArmor.setText(Integer.toString(randomHero.getArmor()));
    }

    @FXML
    protected void onDeleteHeroButtonClick() {
        Hero selectedHero = heroList.getSelectionModel().getSelectedItem();
        if (selectedHero == null) {
            showErrorDialog("No item selected");
        }
        heroes.remove(selectedHero);
    }

    @FXML
    protected void onUpHeroButtonClick() {
        Hero selectedHero = heroList.getSelectionModel().getSelectedItem();
        int idx = heroes.indexOf(selectedHero);
        try {
            Collections.swap(heroes, idx, idx-1);
        } catch (IndexOutOfBoundsException e) {
            showErrorDialog("Can't move item in the direction selected");
        }
    }

    @FXML
    protected void onDownHeroButtonClick() {
        Hero selectedHero = heroList.getSelectionModel().getSelectedItem();
        int idx = heroes.indexOf(selectedHero);
        try {
            Collections.swap(heroes, idx, idx+1);
        } catch (IndexOutOfBoundsException e) {
            showErrorDialog("Can't move item in the direction selected");
        }
    }

    @FXML
    protected void onAddBeastButtonClick() {
        try {
            Beast beast = characterService.createBeast(
                    beastClass.getValue(),
                    beastName.getText(),
                    beastHealth.getText(),
                    beastArmor.getText()
            );
            beasts.add(beast);

            cleanTextInput(beastName, beastHealth, beastArmor);
            beastClass.setValue("");
        } catch (InvalidInput e) {
            showErrorDialog(e.getMessage());
        }
    }

    @FXML
    protected void onRandomBeastButtonClick() {
        Beast randomBeast = CharacterFactory.createRandomBeast();

        beastName.setText(randomBeast.getName());
        beastClass.setValue(randomBeast.getBeastClass().toString());
        beastHealth.setText(Integer.toString(randomBeast.getHealth()));
        beastArmor.setText(Integer.toString(randomBeast.getArmor()));
    }

    @FXML
    protected void onDeleteBeastButtonClick() {
        Beast selectedBeast = beastList.getSelectionModel().getSelectedItem();
        if (selectedBeast == null) {
            showErrorDialog("No item selected");
        }
        beasts.remove(selectedBeast);
    }

    @FXML
    protected void onUpBeastButtonClick() {
        Beast selectedBeast = beastList.getSelectionModel().getSelectedItem();
        int idx = beasts.indexOf(selectedBeast);
        try {
            Collections.swap(beasts, idx, idx-1);
        } catch (IndexOutOfBoundsException e) {
            showErrorDialog("Can't move item in the direction selected");
        }
    }

    @FXML
    protected void onDownBeastButtonClick() {
        Beast selectedBeast = beastList.getSelectionModel().getSelectedItem();
        int idx = beasts.indexOf(selectedBeast);
        try {
            Collections.swap(beasts, idx, idx+1);
        } catch (IndexOutOfBoundsException e) {
            showErrorDialog("Can't move item in the direction selected");
        }
    }

    @FXML
    protected void onFightButtonClick() {
        if (beasts.isEmpty() || heroes.isEmpty()) {
            showErrorDialog("Both armies must have soldiers in order to fight");
        } else {
            battleLog.setText("");
            fightButton.setDisable(true);

            BattleEngine battleEngine = new BattleEngine(beasts, heroes);

            subscribeToBattleEvents(battleEngine);
            battleEngine.start();
        }
    }

    private void cleanTextInput(TextInputControl... textInputControls) {
        Arrays.stream(textInputControls).forEach(t -> t.setText(""));
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void subscribeToBattleEvents(BattleEngine battleEngine) {
        battleEngine.getEventStream()
                .buffer(100, TimeUnit.MILLISECONDS)
                .subscribe(batch -> {
                    StringBuilder sb = new StringBuilder();
                    for (BattleEvent event : batch) {
                        String logText = switch (event) {
                            case RoundStart r -> r.toString();
                            case FightResult fr -> fr.toString();
                            case CharacterDeath d -> d.character().getName() + " has died.";
                            case GameOver g -> {
                                fightButton.setDisable(false);
                                yield g.message();
                            }
                        };
                        sb.append(logText).append("\n");
                    }

                    Platform.runLater(() -> battleLog.appendText(sb.toString()));
                });
    }


}