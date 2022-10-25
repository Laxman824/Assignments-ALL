--1--
with recursive h
    ( train_no , train_name , distance ,     --CET(common etable which is temporary
source_station_name , departure_time ,  day_of_departure ,
destination_station_name , arrival_time , day_of_arrival , hops )
as
(


 select train_no , train_name , distance ,	--conditions given  (non recursive loop)
source_station_name , departure_time ,  day_of_departure ,
destination_station_name , arrival_time , day_of_arrival , 0
 from train_info 
 where source_station_name = 'KURLA' and train_no = 97131

 union all 
 			--recursive loop to get below attributes also 
 select train_info.train_no , train_info.train_name ,train_info.distance ,
h.source_station_name ,train_info.departure_time ,  h.day_of_departure ,
train_info.destination_station_name , train_info.arrival_time , train_info.day_of_arrival , hops+1
	from h 
	join train_info on h.destination_station_name = train_info.source_station_name
    where hops < 2
  
  )
  
  
 select distinct(destination_station_name)
 from h
 order by destination_station_name

--2--

with recursive h
    ( train_no , train_name , distance ,     --CET(common etable which is temporary
source_station_name , departure_time ,  day_of_departure ,
destination_station_name , arrival_time , day_of_arrival , hops )
as 
(

(
 select train_no , train_name , distance ,	--conditions given  (non recursive loop)
source_station_name , departure_time ,  day_of_departure ,
destination_station_name , arrival_time , day_of_arrival , 0
 from train_info 
 where source_station_name = 'KURLA' and train_no = 97131 and train_info.day_of_arrival = h.day_of_departture 

 union all 
 
 
 					--recursive loop to get below attributes also 
 select train_info.train_no , train_info.train_name ,train_info.distance ,
h.source_station_name ,train_info.departure_time ,  h.day_of_departure ,
train_info.destination_station_name , train_info.arrival_time , train_info.day_of_arrival , hops+1
	from h  
	join train_info on h.destination_station_name = train_info.source_station_name
    where hops < 2 and t1.day_of_departure= t2.day_of_arrival)
  
  )
  
  
 select distinct(destination_station_name)
 from h
 order by destination_station_name

  
  
 ----------niky
with atmost as 
 ( with t1 as(
 select train_info.destination_station_name,train_info.train_no,train_info.source_station_name	
 	from train_info
 	where (train_no = 97131 and source_station_name ='Kurla')
 ),
 	
 t2 as (	
 	
  select train_info.destination_station_name 
 from train_info join t1 on (t1.destination_station_name = train_info.source_station_name))
 ),
     t3 as 
     ( select  train_info.destination_station_name
     
     from train_info join t2 on (t2.destination_station_name= train.info.source_station_name)
     
     ),
     atmost as 
     ( select * 
     from t1 union t2 union t3
     
     )select distinct(destination_station_name) from atmost
  	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
  
