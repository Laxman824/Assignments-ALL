
CREATE TABLE  IF NOT EXISTS leagues (
    leagueid bigint NOT NULL,
    name text,
    CONSTRAINT leagueid PRIMARY KEY (leagueid)
);

CREATE TABLE  IF NOT EXISTS players (
    playerid bigint NOT NULL,
    name text,
    CONSTRAINT playerid PRIMARY KEY (playerid)
);

CREATE TABLE  IF NOT EXISTS teams (
    teamid bigint NOT NULL,
    name text,
    CONSTRAINT teamid PRIMARY KEY (teamid)
);

CREATE TABLE  IF NOT EXISTS appearances (
    gameid bigint NOT NULL,
    playerid bigint,
    leagueid bigint,
    goals bigint,
    owngoals bigint,
    assists bigint,
    keypasses bigint,
    shots bigint
);

CREATE table IF NOT EXISTS games(
    gameid bigint NOT NULL,
    leagueid bigint,
    hometeamid bigint,
    awayteamid bigint,
    year bigint,
    homegoals bigint,
    awaygoals bigint,
    CONSTRAINT game_id PRIMARY KEY (gameid),
    CONSTRAINT hometeamid FOREIGN KEY (hometeamid) references teams(teamid),
    CONSTRAINT awayteamid FOREIGN KEY (awayteamid) references teams(teamid)
);

\copy appearance from '/home/laxman/Downloads/SEMESTER 8/COL362(dbms)/ASS2/appearance.csv' delimiter ',' csv header;
\copy games from '/home/laxman/Downloads/SEMESTER 8/COL362(dbms)/ASS2/games.csv' delimiter ',' csv header;
\copy leagues from '/home/laxman/Downloads/SEMESTER 8/COL362(dbms)/ASS2/leagues.csv' delimiter ',' csv header;
\copy players from '/home/laxman/Downloads/SEMESTER 8/COL362(dbms)/ASS2/palyers.csv' delimiter ',' csv header;
\copy teams from '/home/laxman/Downloads/SEMESTER 8/COL362(dbms)/ASS2/teams.csv' delimiter ',' csv header;
