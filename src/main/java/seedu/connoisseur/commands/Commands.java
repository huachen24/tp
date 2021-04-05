package seedu.connoisseur.commands;

import seedu.connoisseur.exceptions.ConnoisseurException;
import seedu.connoisseur.storage.ConnoisseurData;
import seedu.connoisseur.storage.Storage;
import seedu.connoisseur.ui.Ui;


import static seedu.connoisseur.messages.Messages.INVALID_COMMAND;


/**
 * Class with methods for different commands.
 */
public class Commands {

    private final Ui ui;
    private final Storage storage;
    private boolean isReviewMode = true;
    private final ReviewList reviewList;
    private final RecommendationList recommendationList;

    /**
     * Creates Commands based on stored data.
     *
     * @param connoisseurData locally stored data
     * @param ui              ui instance
     * @param storage         storage instance
     */
    public Commands(ConnoisseurData connoisseurData, Ui ui, Storage storage) {
        this.ui = ui;
        this.storage = storage;
        reviewList = new ReviewList(connoisseurData, ui);
        recommendationList = new RecommendationList(connoisseurData, ui, reviewList);
    }

    /**
     * Creates new tasks if no existing data in files.
     */
    public Commands(Ui ui, Storage storage) {
        this.ui = ui;
        this.storage = storage;
        reviewList = new ReviewList(ui);
        recommendationList = new RecommendationList(ui, reviewList);
    }

    /**
     * Goes into review mode.
     */
    public void reviewMode() {
        isReviewMode = true;
        ui.println("You are now in review mode");
    }

    /**
     * Goes into recommendation mode.
     */
    public void recommendationMode() {
        isReviewMode = false;
        ui.println("You are now in recommendation mode");
    }

    /**
     * Changes display to stars or asterisks.
     *
     * @param displayType stars or asterisks
     */
    public void display(String displayType) {
        if (isReviewMode) {
            reviewList.changeDisplay(displayType);
        } else {
            ui.printCommandDoesNotExistInRecommendationMode();
        }
    }

    /**
     * Print text to help user with using the application.
     *
     * @param arguments is the type of help determined by user input.
     */
    public void printHelp(String arguments) {
        if (arguments == null || arguments.equals("")) {
            ui.printGeneralHelpMessage();
        } else if (arguments.equals("sort")) {
            ui.printSortHelpMessage();
        } else if (arguments.equals("list")) {
            ui.printListHelpMessage();
        } else if (arguments.equals("edit")) {
            ui.printEditHelpMessage();
        } else if (arguments.equals("new") || arguments.equals("add")) {
            ui.printNewHelpMessage();
        } else if (arguments.equals("delete")) {
            ui.printDeleteHelpMessage();
        } else if (arguments.equals("view")) {
            ui.printViewHelpMessage();
        } else if (arguments.equals("display")) {
            ui.printDisplayHelpMessage();
        } else if (arguments.equals("review")) {
            ui.printReviewModeHelpMessage();
        } else if (arguments.equals("reco")) {
            ui.printRecommendationModeHelpMessage();
        } else if (arguments.equals("done")) {
            ui.printRecoDoneHelpMessage();
        } else if (arguments.equals("exit") || arguments.equals("bye")) {
            ui.printExitHelpMessage();
        } else {
            ui.printInvalidHelpMessage();
        }
    }

    /**
     * Print invalid command text.
     */
    public void invalidCommand() {
        ui.println(INVALID_COMMAND);
    }

    /**
     * Print invalid parameters text.
     */
    public void invalidParameters() {
        ui.println("Invalid command. Please do not enter extra parameters or less parameters than required.");
    }

    /**
     * Exits connoisseur.
     */
    public void exit() {
        storage.saveConnoisseurData(reviewList.sorter.getSortMethod(), reviewList.getDisplayStars(),
                reviewList.reviews, recommendationList.recommendations);
        ui.printExitMessage();
    }

    /**
     * List reviews or recommendations depending on the mode.
     *
     * @param input is the sorting preference used to list, only applicable in review mode.
     */
    public void list(String input) {
        if (isReviewMode) {
            reviewList.listReviews(input);
        } else {
            recommendationList.listRecommendations();
        }
    }

    /**
     * Sort reviews depending on the mode.
     *
     * @param sortType is the sorting preference, only applicable in review mode.
     */
    public void sort(String sortType) {
        if (isReviewMode) {
            reviewList.sortReviews(sortType);
        } else {
            ui.printCommandDoesNotExistInRecommendationMode();
        }
    }

    /**
     * Delete reviews or recommendations depending on the mode.
     *
     * @param title is the title of review or recommendation to be deleted.
     */
    public void delete(String title) {
        title = title.toLowerCase();
        if (isReviewMode) {
            reviewList.deleteReview(title);
        } else {
            recommendationList.deleteRecommendation(title);
        }
    }


    /**
     * View a selected review.
     *
     * @param title title of the review to be viewed.
     */
    public void view(String title) {
        title = title.toLowerCase();
        if (isReviewMode) {
            reviewList.viewReview(title);
        } else {
            ui.printCommandDoesNotExistInRecommendationMode();
        }
    }

    /**
     * Removes selected recommended from list and converts it to a review.
     *
     * @param title title of the review to be viewed.
     */
    public void done(String title) {
        title = title.toLowerCase();
        if (isReviewMode) {
            ui.printCommandDoesNotExistInReviewMode();
        } else {
            recommendationList.convertRecommendation(title);
        }
    }


    /**
     * Add a selected review or recommendation.
     *
     * @param input will be quick or long in review mode.
     *              will be the recommendation title in the recommendation mode.
     */
    public void add(String input) {
        if (isReviewMode) {
            reviewList.addReview(input);
        } else {
            recommendationList.addRecommendation();
        }
    }

    /**
     * Edits a review.
     *
     * @param title title of review
     */
    public void edit(String title) {
        if (isReviewMode) {
            reviewList.editReview(title);
        } else {
            recommendationList.editRecommendation(title);
        }
    }
}