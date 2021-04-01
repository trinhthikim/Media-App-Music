package com.example.myplaymusic_kim;

public class Song {
    public int id;
    public String name;
    public int img;
    public int src;
    public String singer;
    public Song () {
        id = 0;
        this.name = "Anh da sai";
        this.img = R.drawable.anh_da_sai;
        this.src = R.raw.anh_da_sai;
        this.singer = "Only C";
    }
    public Song (Song song) {
        this.name = song.name;
        this.img = song.img;
        this.src = song.src;
        this.singer = song.singer;
    }
}
