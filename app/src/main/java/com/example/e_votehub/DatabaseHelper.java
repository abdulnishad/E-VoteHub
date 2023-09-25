package com.example.e_votehub;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UserData.db";
    private static final String TABLE_USERS = "users";
    private static final String TABLE_ADMINS = "admins";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_EMAIL = "email";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";
    private static final String TABLE_VOTES = "votes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_POSITION = "position";
    private static final String COLUMN_USERS = "users";

    private static final String TABLE_CANDIDATES = "candidates";
    private static final String COL_CANDIDATE_ID = "candidate_id";
    private static final String COL_FULL_NAME = "full_name";
    private static final String COL_POSITION = "position";
    private static final String COL_VOTE_ID = "vote_id"; // Foreign key column referencing the vote table
    private static final String TABLE_USER_VOTES = "user_votes";
    private static final String COL_USER_VOTE_ID = "user_vote_id"; // Autoincrement ID for user votes
    private static final String COL_USER_ID = "user_id"; // User ID associated with the vote
    private static final String COL_SELECTED_CANDIDATE_ID = "selected_candidate_id"; // The ID of the selected candidate
    private static final String TABLE_SESSION = "session";
    private static final String COL_SESSION_ID = "session_id";
    private static final String COL_LOGGED_IN_USER_ID = "user_id";
    private static final String TABLE_RESULT = "result";
    private static final String COL_RESULT_ID = "result_id";

    private static final String COL_VOTE_COUNT = "vote_count";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTableQuery = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_EMAIL + " TEXT, " +
                COL_USERNAME + " TEXT, " +
                COL_PASSWORD + " TEXT)";

        String createAdminTableQuery = "CREATE TABLE " + TABLE_ADMINS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT UNIQUE, " +
                COL_PASSWORD + " TEXT)";
        String createVoteTableQuery = "CREATE TABLE " + TABLE_VOTES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_POSITION + " TEXT,"
                + COLUMN_USERS + " TEXT"
                + ")";
        String createCandidateTableQuery = "CREATE TABLE " + TABLE_CANDIDATES + "("
                + COL_CANDIDATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_FULL_NAME + " TEXT,"
                + COL_EMAIL + " TEXT,"
                + COL_POSITION + " TEXT,"
                + COL_VOTE_ID + " INTEGER,"
                + "FOREIGN KEY(" + COL_VOTE_ID + ") REFERENCES " + TABLE_VOTES + "(" + COLUMN_ID + ")"
                + ")";
        String createUserVotesTableQuery = "CREATE TABLE " + TABLE_USER_VOTES + "("
                + COL_USER_VOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_USER_ID + " INTEGER,"
                + COL_VOTE_ID + " INTEGER,"
                + COL_SELECTED_CANDIDATE_ID + " INTEGER,"
                + "FOREIGN KEY(" + COL_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COL_ID + "),"
                + "FOREIGN KEY(" + COL_VOTE_ID + ") REFERENCES " + TABLE_VOTES + "(" + COLUMN_ID + "),"
                + "FOREIGN KEY(" + COL_SELECTED_CANDIDATE_ID + ") REFERENCES " + TABLE_CANDIDATES + "(" + COL_CANDIDATE_ID + ")"
                + ")";
        String createSessionTableQuery = "CREATE TABLE " + TABLE_SESSION + "("
                + COL_SESSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_LOGGED_IN_USER_ID + " INTEGER"
                + ")";
        String createResultTableQuery = "CREATE TABLE " + TABLE_RESULT + "("
                + COL_RESULT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_VOTE_ID + " INTEGER,"
                + COL_CANDIDATE_ID + " INTEGER,"
                + COL_USER_ID + " INTEGER,"
                + COL_VOTE_COUNT + " INTEGER,"
                + "FOREIGN KEY(" + COL_VOTE_ID + ") REFERENCES " + TABLE_VOTES + "(" + COLUMN_ID + "),"
                + "FOREIGN KEY(" + COL_CANDIDATE_ID + ") REFERENCES " + TABLE_CANDIDATES + "(" + COL_CANDIDATE_ID + "),"
                + "FOREIGN KEY(" + COL_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COL_ID + ")"
                + ")";

        db.execSQL(createResultTableQuery);
        db.execSQL(createSessionTableQuery);
        db.execSQL(createUserVotesTableQuery);
        db.execSQL(createCandidateTableQuery);
        db.execSQL(createUserTableQuery);
        db.execSQL(createAdminTableQuery);
        db.execSQL(createVoteTableQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMINS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CANDIDATES);
        onCreate(db);
    }
    public void setLoggedInUserId(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_LOGGED_IN_USER_ID, userId);

        db.delete(TABLE_SESSION, null, null); // Clear any previous session data
        db.insert(TABLE_SESSION, null, values); // Insert the current session data
    }

    // Method to get the logged-in user ID from the session table
    @SuppressLint("Range")
    public int getLoggedInUserId() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_LOGGED_IN_USER_ID};
        Cursor cursor = db.query(TABLE_SESSION, columns, null, null, null, null, null);

        int userId = -1;
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex(COL_LOGGED_IN_USER_ID));
        }

        cursor.close();
        return userId;
    }

    public boolean addUser(String name, String email, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_EMAIL, email);
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, contentValues);
        return result != -1;
    }

    @SuppressLint("Range")
    public String getLoggedInUser() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {TABLE_USERS + "." + COL_USERNAME}; // Include the table name to avoid ambiguity
        String selection = TABLE_SESSION + "." + COL_LOGGED_IN_USER_ID + " = " + TABLE_USERS + "." + COL_ID;

        Cursor cursor = db.query(TABLE_SESSION + " JOIN " + TABLE_USERS,
                columns,
                selection,
                null,
                null,
                null,
                null);

        String username = null;
        if (cursor.moveToFirst()) {
            username = cursor.getString(cursor.getColumnIndex(COL_USERNAME));
        }

        cursor.close();
        return username;
    }
    public boolean checkUserExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_ID};
        String selection = COL_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }
    public boolean checkUserCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_ID};
        String selection = COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }
    @SuppressLint("Range")
    public int checkUserCredentialsid(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_ID};
        String selection = COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        int userId = -1; // Initialize with an invalid user ID
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex(COL_ID));
        }

        cursor.close();
        return userId;
    }



    public boolean addAdminCredentials(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_PASSWORD, password);

        long result = db.insert(TABLE_ADMINS, null, contentValues);
        return result != -1;
    }

    public boolean checkAdminCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_ID};
        String selection = COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_ADMINS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    // Method to get all users from the database
    @SuppressLint("Range")
    public List<UserModel> getAllUsers() {
        List<UserModel> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);

        if (cursor.moveToFirst()) {
            do {
                UserModel user = new UserModel();
                user.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
                user.setName(cursor.getString(cursor.getColumnIndex(COL_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COL_EMAIL)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(COL_USERNAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COL_PASSWORD)));

                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return userList;
    }


    public long addVote(String position, List<String> selectedUsers) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_POSITION, position);
        values.put(COLUMN_USERS, convertListToString(selectedUsers));

        return db.insert(TABLE_VOTES, null, values);
    }



    private String convertListToString(List<String> list) {
        StringBuilder builder = new StringBuilder();
        for (String item : list) {
            builder.append(item).append(",");
        }
        return builder.toString();
    }

    private List<String> convertStringToList(String input) {
        List<String> list = new ArrayList<>();
        String[] items = input.split(",");
        for (String item : items) {
            list.add(item);
        }
        return list;
    }



    public long addCandidate(String fullName, String position, long voteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_FULL_NAME, fullName);
        values.put(COL_POSITION, position);
        values.put(COL_VOTE_ID, voteId);

        // Insert the candidate data into the table
        return db.insert(TABLE_CANDIDATES, null, values);
    }

    public List<VoteModel> getAllVotes() {
        List<VoteModel> voteList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_VOTES, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int voteId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String voteTitle = cursor.getString(cursor.getColumnIndex(COLUMN_POSITION));
                // Add other properties as needed

                // Create a new VoteModel object and add it to the list
                VoteModel vote = new VoteModel(voteId, voteTitle);
                voteList.add(vote);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return voteList;
    }

    @SuppressLint("Range")
    public String getVotePositionById(int voterId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_POSITION};
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(voterId)};
        Cursor cursor = db.query(TABLE_VOTES, columns, selection, selectionArgs, null, null, null);

        String position = null;
        if (cursor.moveToFirst()) {
            position = cursor.getString(cursor.getColumnIndex(COLUMN_POSITION));
        }

        cursor.close();
        return position;
    }


    @SuppressLint("Range")
    public List<CandidateModel> getCandidatesByVoteId(int voteId) {
        List<CandidateModel> candidates = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("DatabaseQuery", "SELECT * FROM " + TABLE_CANDIDATES + " WHERE " + COL_VOTE_ID + " = " + voteId);

        Cursor cursor = db.query(
                TABLE_CANDIDATES,
                new String[]{COL_CANDIDATE_ID, COL_FULL_NAME, COL_POSITION},
                COL_VOTE_ID + " = ?",
                new String[]{String.valueOf(voteId)},
                null,
                null,
                null
        );

        try {
            String[] columns = {COL_CANDIDATE_ID, COL_FULL_NAME, COL_POSITION};
            String selection = COL_VOTE_ID + " = ?";
            String[] selectionArgs = {String.valueOf(voteId)};
            cursor = db.query(TABLE_CANDIDATES, columns, selection, selectionArgs, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    int candidateId = cursor.getInt(cursor.getColumnIndex(COL_CANDIDATE_ID));
                    String fullName = cursor.getString(cursor.getColumnIndex(COL_FULL_NAME));
                    String position = cursor.getString(cursor.getColumnIndex(COL_POSITION));

                    CandidateModel candidate = new CandidateModel(candidateId, fullName, position);
                    candidates.add(candidate);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Make sure to close the Cursor to release resources
            if (cursor != null) {
                cursor.close();
            }
        }

        return candidates;
    }






    public long addUserVoteResult(int voteId, int selectedCandidateId, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_VOTE_ID, voteId);
        values.put(COL_CANDIDATE_ID, selectedCandidateId);
        values.put(COL_USER_ID, userId);
        values.put(COL_VOTE_COUNT, 1); // Initialize vote count to 1

        // Insert the vote result data into the ResultTable
        return db.insert(TABLE_RESULT, null, values);
    }
    public boolean hasUserVoted(int voteId, int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_RESULT_ID};
        String selection = COL_VOTE_ID + " = ? AND " + COL_USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(voteId), String.valueOf(userId)};
        Cursor cursor = db.query(TABLE_RESULT, columns, selection, selectionArgs, null, null, null);

        boolean hasVoted = cursor.getCount() > 0;
        cursor.close();
        return hasVoted;
    }
    @SuppressLint("Range")
    public String getUsernameFromUserId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_USERNAME};
        String selection = COL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        String username = null;
        if (cursor.moveToFirst()) {
            username = cursor.getString(cursor.getColumnIndex(COL_USERNAME));
        }

        cursor.close();
        return username;
    }
    @SuppressLint("Range")
    public List<VoteModel> getVotesForUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<VoteModel> voteList = new ArrayList<>();
        String formattedUsername = "," + username + ",";
        // Query the VoteTable using the retrieved username
        String[] columns = {COLUMN_ID, COLUMN_POSITION, COLUMN_USERS};
        String selection = COLUMN_USERS + " LIKE ?";
        String[] selectionArgs = {"%" + username + "%"};

        Cursor cursor = db.query(TABLE_VOTES, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int voteId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String voteTitle = cursor.getString(cursor.getColumnIndex(COLUMN_POSITION));

                VoteModel vote = new VoteModel(voteId, voteTitle);
                voteList.add(vote);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return voteList;
    }
    @SuppressLint("Range")
    public String getCandidateNameById(int candidateId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_FULL_NAME};
        String selection = COL_CANDIDATE_ID + " = ?";
        String[] selectionArgs = {String.valueOf(candidateId)};
        Cursor cursor = db.query(TABLE_CANDIDATES, columns, selection, selectionArgs, null, null, null);

        String candidateName = null;
        if (cursor.moveToFirst()) {
            candidateName = cursor.getString(cursor.getColumnIndex(COL_FULL_NAME));
        }

        cursor.close();
        return candidateName;
    }
    @SuppressLint("Range")
    public List<ResultModel> getVoteResults(int voteId) {
        List<ResultModel> resultModels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                COL_CANDIDATE_ID,
                "COUNT(" + COL_VOTE_ID + ") AS " + COL_VOTE_COUNT
        };
        String selection = COL_VOTE_ID + " = ?";
        String[] selectionArgs = {String.valueOf(voteId)};
        String groupBy = COL_CANDIDATE_ID;

        Cursor cursor = db.query(
                TABLE_RESULT,
                columns,
                selection,
                selectionArgs,
                COL_CANDIDATE_ID,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int candidateId = cursor.getInt(cursor.getColumnIndex(COL_CANDIDATE_ID));
                int voteCount = cursor.getInt(cursor.getColumnIndex(COL_VOTE_COUNT));

                String candidateName = getCandidateNameById(candidateId);
                ResultModel resultModel = new ResultModel(candidateId, candidateName, voteCount);
                resultModels.add(resultModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return resultModels;
    }

    public boolean deleteVote(int voteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete the vote from the votes table where the voteId matches
        int result = db.delete(TABLE_VOTES, COLUMN_ID + "=?", new String[]{String.valueOf(voteId)});
        // Delete the candidates associated with the vote from the candidates table
        db.delete(TABLE_CANDIDATES, COL_VOTE_ID + "=?", new String[]{String.valueOf(voteId)});
        // Delete the user votes associated with the vote from the user_votes table
        db.delete(TABLE_USER_VOTES, COL_VOTE_ID + "=?", new String[]{String.valueOf(voteId)});
        // Delete the vote results associated with the vote from the result table
        db.delete(TABLE_RESULT, COL_VOTE_ID + "=?", new String[]{String.valueOf(voteId)});

        // If at least one row is affected, return true; otherwise, return false
        return result > 0;
    }



}
