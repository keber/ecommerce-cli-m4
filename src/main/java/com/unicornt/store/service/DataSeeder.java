package com.unicornt.store.service;

import com.unicornt.store.model.Product;

/**
 * Loads the 49 real products from the Unicorn't Store catalog into a Catalog instance.
 * Category field maps to the thematic subcategory (e.g., "it-crowd", "devops"),
 * which is the relevant dimension for discount rules.
 */
public class DataSeeder {

    private DataSeeder() {}

    public static void populate(Catalog catalog) {

        // ── PM ───────────────────────────────────────────────────────────────
        catalog.add(new Product(1,  "Polera 'I Can Explain It To You'",                          "pm",          13990));

        // ── Cloud ─────────────────────────────────────────────────────────────
        catalog.add(new Product(2,  "Polera 'Cloud Architect'",                                  "cloud",       14990));

        // ── DevOps ───────────────────────────────────────────────────────────
        catalog.add(new Product(3,  "Polera 'Breaking Prod'",                                    "devops",      13990));
        catalog.add(new Product(4,  "Polera 'CI/CD or Die Trying'",                              "devops",      13990));
        catalog.add(new Product(5,  "Polera 'DevOps Acronym'",                                   "devops",      12990));
        catalog.add(new Product(6,  "Polera 'I Broke Prod Again'",                               "devops",      13990));
        catalog.add(new Product(7,  "Polera 'It Works In My Container'",                         "devops",      13990));
        catalog.add(new Product(8,  "Polera 'No Deploy on Fridays'",                             "devops",      14990));

        // ── Enigma ────────────────────────────────────────────────────────────
        catalog.add(new Product(9,  "Polera 'Enigma Blueprint'",                                 "enigma",      15990));
        catalog.add(new Product(10, "Polera 'Enigma Machine'",                                   "enigma",      15990));

        // ── General ───────────────────────────────────────────────────────────
        catalog.add(new Product(11, "Polera 'Don Ramon: La Venganza'",                           "general",     12990));
        catalog.add(new Product(12, "Polera 'Don Ramon: La Venganza II'",                        "general",     12990));
        catalog.add(new Product(13, "Polera 'No Lloren Por Mi'",                                 "general",     12990));
        catalog.add(new Product(14, "Polera 'Stonks'",                                           "general",     12990));
        catalog.add(new Product(15, "Polera 'This Is Fine'",                                     "general",     12990));

        // ── IT Crowd ──────────────────────────────────────────────────────────
        catalog.add(new Product(16, "Polera '0118 999 881 999 119 725 3'",                       "it-crowd",    14990));
        catalog.add(new Product(17, "Polera 'RTFM'",                                             "it-crowd",    12990));
        catalog.add(new Product(18, "Polera 'Choose Your Weapon'",                               "it-crowd",    13990));
        catalog.add(new Product(19, "Polera 'I Don't Work Here'",                                "it-crowd",    13990));
        catalog.add(new Product(20, "Polera 'I Hope This Email Finds You Well'",                 "it-crowd",    13990));
        catalog.add(new Product(21, "Polera 'I Read Your Email'",                                "it-crowd",    13990));
        catalog.add(new Product(22, "Polera 'I See Dumb People'",                                "it-crowd",    13990));
        catalog.add(new Product(23, "Polera 'Type Google Into Google'",                          "it-crowd",    12990));
        catalog.add(new Product(24, "Polera 'Meh'",                                              "it-crowd",    11990));
        catalog.add(new Product(25, "Polera 'Moss: Keep Calm'",                                  "it-crowd",    13990));
        catalog.add(new Product(26, "Polera 'Lo Apagaste y lo Volviste a Encender?'",            "it-crowd",    14990));
        catalog.add(new Product(27, "Polera 'Music I Like'",                                     "it-crowd",    12990));
        catalog.add(new Product(28, "Polera 'Pixel Pirate Flag'",                                "it-crowd",    13990));
        catalog.add(new Product(29, "Polera 'Roy: People, What a Bunch of Bastards'",            "it-crowd",    14990));
        catalog.add(new Product(30, "Polera 'The Cake Is a Lie'",                                "it-crowd",    12990));
        catalog.add(new Product(31, "Polera 'The Sun Is Trying to Kill Me'",                     "it-crowd",    13990));

        // ── Linux ─────────────────────────────────────────────────────────────
        catalog.add(new Product(32, "Polera 'sudo rm -rf /'",                                    "linux",       14990));

        // ── Personajes ───────────────────────────────────────────────────────
        catalog.add(new Product(33, "Polera 'AC/DC: Tesla vs Edison'",                           "personajes",  15990));
        catalog.add(new Product(34, "Polera 'Alan Turing'",                                      "personajes",  15990));
        catalog.add(new Product(35, "Polera 'Chuck Norris Doesn't Code'",                        "personajes",  13990));
        catalog.add(new Product(36, "Polera 'Nikola Tesla'",                                     "personajes",  14990));
        catalog.add(new Product(37, "Polera 'Chuck Norris Facts'",                               "personajes",  13990));
        catalog.add(new Product(38, "Polera 'Turing Test'",                                      "personajes",  14990));

        // ── Programador ───────────────────────────────────────────────────────
        catalog.add(new Product(39, "Polera 'C: You Have No Class'",                             "programador", 13990));
        catalog.add(new Product(40, "Polera 'CSS Is Awesome'",                                   "programador", 12990));
        catalog.add(new Product(41, "Polera 'CTM Compilara Todo Manana'",                        "programador", 13990));
        catalog.add(new Product(42, "Polera 'False: It's Funny Because It's True'",              "programador", 12990));
        catalog.add(new Product(43, "Polera 'I Don't Always Test My Code'",                      "programador", 13990));
        catalog.add(new Product(44, "Polera 'I'm Just Here for the Pizza'",                      "programador", 12990));
        catalog.add(new Product(45, "Polera 'Coffee + Problem = Programmer'",                    "programador", 12990));
        catalog.add(new Product(46, "Polera 'Programming Is 10 Percent Writing Code'",           "programador", 13990));
        catalog.add(new Product(47, "Polera 'This Meeting Could Have Been an Email'",            "programador", 14990));

        // ── QA ────────────────────────────────────────────────────────────────
        catalog.add(new Product(48, "Polera 'Quality Assurance'",                                "qa",          13990));
        catalog.add(new Product(49, "Polera 'Quality Assurance Vol. 2'",                         "qa",          13990));
    }
}
