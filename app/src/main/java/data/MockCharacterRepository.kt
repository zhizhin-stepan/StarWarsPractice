package com.zhizhin.starwarspractice.data

import com.zhizhin.starwarspractice.domain.StarWarsCharacter

class MockCharacterRepository : CharacterRepository {

    override fun getCharacters(): List<StarWarsCharacter> {
        return listOf(
            StarWarsCharacter(
                id = 1,
                name = "Luke Skywalker",
                height = "172",
                mass = "77",
                hairColor = "blond",
                skinColor = "fair",
                eyeColor = "blue",
                birthYear = "19BBY",
                gender = "male",
                homeworld = "Tatooine"
            ),
            StarWarsCharacter(
                id = 2,
                name = "C-3PO",
                height = "167",
                mass = "75",
                hairColor = "n/a",
                skinColor = "gold",
                eyeColor = "yellow",
                birthYear = "112BBY",
                gender = "n/a",
                homeworld = "Tatooine"
            ),
            StarWarsCharacter(
                id = 3,
                name = "R2-D2",
                height = "96",
                mass = "32",
                hairColor = "n/a",
                skinColor = "white, blue",
                eyeColor = "red",
                birthYear = "33BBY",
                gender = "n/a",
                homeworld = "Naboo"
            ),
            StarWarsCharacter(
                id = 4,
                name = "Darth Vader",
                height = "202",
                mass = "136",
                hairColor = "none",
                skinColor = "white",
                eyeColor = "yellow",
                birthYear = "41.9BBY",
                gender = "male",
                homeworld = "Tatooine"
            ),
            StarWarsCharacter(
                id = 5,
                name = "Leia Organa",
                height = "150",
                mass = "49",
                hairColor = "brown",
                skinColor = "light",
                eyeColor = "brown",
                birthYear = "19BBY",
                gender = "female",
                homeworld = "Alderaan"
            ),
            StarWarsCharacter(
                id = 6,
                name = "Owen Lars",
                height = "178",
                mass = "120",
                hairColor = "brown, grey",
                skinColor = "light",
                eyeColor = "blue",
                birthYear = "52BBY",
                gender = "male",
                homeworld = "Tatooine"
            ),
            StarWarsCharacter(
                id = 7,
                name = "Beru Whitesun lars",
                height = "165",
                mass = "75",
                hairColor = "brown",
                skinColor = "light",
                eyeColor = "blue",
                birthYear = "47BBY",
                gender = "female",
                homeworld = "Tatooine"
            ),
            StarWarsCharacter(
                id = 8,
                name = "R5-D4",
                height = "97",
                mass = "32",
                hairColor = "n/a",
                skinColor = "white, red",
                eyeColor = "red",
                birthYear = "unknown",
                gender = "n/a",
                homeworld = "Tatooine"
            ),
            StarWarsCharacter(
                id = 9,
                name = "Biggs Darklighter",
                height = "183",
                mass = "84",
                hairColor = "black",
                skinColor = "light",
                eyeColor = "brown",
                birthYear = "24BBY",
                gender = "male",
                homeworld = "Tatooine"
            ),
            StarWarsCharacter(
                id = 10,
                name = "Obi-Wan Kenobi",
                height = "182",
                mass = "77",
                hairColor = "auburn, grey",
                skinColor = "fair",
                eyeColor = "blue-gray",
                birthYear = "57BBY",
                gender = "male",
                homeworld = "Stewjon"
            )
        )
    }
}