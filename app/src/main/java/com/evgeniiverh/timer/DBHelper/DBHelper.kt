package com.evgeniiverh.timer.DBHelper

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.evgeniiverh.timer.MainActivity
import com.evgeniiverh.timer.asset.Person

class DBHelper(context: MainActivity):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VER) {
    companion object{
        private val DATABASE_VER=1
        private val DATABASE_NAME="EDMTB.db"

        //Tabl
        private val TABLE_NAME="Person"
        private val COL_ID="Id"
        private val COL_NAME="Name"
        private val COL_DATE="Date"
        private val COL_TIME="Time"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY=("CREATE TABLE $TABLE_NAME($COL_ID INTEGER PRIMARY KEY,$COL_NAME TEXT,$COL_DATE TEXT,$COL_TIME TEXT)")
        db!!.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }


    //CRUD
    val allPerson:List<Person>
    get(){
        val listPersons = ArrayList<Person>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db=this.writableDatabase
        val cursor = db.rawQuery(selectQuery,null)
        if(cursor.moveToFirst()){
            do{
                val person = Person()
                person.id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                person.name = cursor.getString(cursor.getColumnIndex(COL_NAME))
                person.date = cursor.getString(cursor.getColumnIndex(COL_DATE))
                person.time= cursor.getString(cursor.getColumnIndex(COL_TIME))

                listPersons.add(person)
            }while (cursor.moveToNext())
        }
        db.close()
        return listPersons
    }

    fun addPerson(person: Person)
    {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID,person.id)
        values.put(COL_NAME,person.name)
        values.put(COL_DATE,person.date)
        values.put(COL_TIME,person.time)

        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    fun updatePerson(person: Person):Int
    {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID,person.id)
        values.put(COL_NAME,person.name)
        values.put(COL_DATE,person.date)
        values.put(COL_TIME,person.time)

        return db.update(TABLE_NAME,values,"$COL_ID=?", arrayOf(person.id.toString()))

    }

    fun deletePerson(person: Person)
    {
        val db = this.writableDatabase

        db.delete(TABLE_NAME,"$COL_ID=?", arrayOf(person.id.toString()))
        db.close()
    }

}