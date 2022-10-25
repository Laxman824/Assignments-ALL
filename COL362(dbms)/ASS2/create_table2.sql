CREATE table IF NOT EXISTS train_info(
    train_no integer ,train_name  text, distance  integer,
source_station_name  text, day_of_departure  text,
destination_station_name  text, day_of_arrival  text , departure_time time , arrival_time  time

);

\copy circuits from '/home/laxman/Downloads/SEMESTER 8/COL362(dbms)/ASS2/train_info.csv' delimiter ',' csv header;
