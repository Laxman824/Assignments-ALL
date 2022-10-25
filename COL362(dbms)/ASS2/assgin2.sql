--1--
with recursive ans as (
select source_station_name,destination_station_name,0 as hops from train_info
where source_station_name ='KURLA' and  train_no = 97131

union 

select  distinct train_info.source_station_name,train_info.destination_station_name,ans.hops+1 from train_info, ans
where ans.destination_station_name  = train_info.source_station_name and ans.hops <2 )
select distinct  (destination_station_name) from ans order by destination_station_name asc;


--2--

with recursive ans as (
select source_station_name,destination_station_name,0 as hops,day_of_departure,day_of_arrival from train_info
where source_station_name ='KURLA' and  train_no = 97131 

union all


select ans.source_station_name,train_info.destination_station_name,ans.hops+1,ans.day_of_departure,train_info.day_of_arrival
from  train_info , ans where ans.destination_station_name = train_info.source_station_name 
	and ans.hops <2 )
select distinct  (destination_station_name) from ans 
where day_of_arrival =day_of_departure
order by destination_station_name asc;

--3--


with recursive ans as (

select train_info.source_station_name,destination_station_name,0 as hops,day_of_departure,day_of_arrival,distance from train_info
where source_station_name ='DADAR' 



union all

select distinct train_info.source_station_name,train_info.destination_station_name,ans.hops+1,
train_info.day_of_departure,ans.day_of_arrival ,(ans.distance+train_info.distance) as distance
from train_info,ans
where ans.destination_station_name = train_info.source_station_name and ans.hops <2 and ans.day_of_arrival = train_info.day_of_departure  ) 
oka
select distinct (destination_station_name) ,distance, day_of_arrival as day from ans  order by destination_station_name asc

--4--
with day_mapping as
(
  select 'Monday' as day, 1 as daynum
  UNION
  select 'Tuesday' as day, 2 as daynum
  UNION
  select 'Wednesday' as day, 3 as daynum
  UNION
  select 'Thursday' as day, 4 as daynum
  UNION
  select 'Friday' as day, 5 as daynum
  UNION
  select 'Saturday' as day, 6 as daynum
  UNION
  select 'Sunday' as day, 7 as daynum
),
t1 as
(
   select destination_station_name, day_of_arrival, arrival_time
   from train_info
   where (source_station_name='DADAR')
),
t2 as
(
   select train_info.destination_station_name, train_info.day_of_arrival, train_info.arrival_time
   from (train_info join t1
         on ((
               t1.destination_station_name = train_info.source_station_name
               and (
                    (select daynum from day_mapping where day = t1.day_of_arrival) <
                    (select daynum from day_mapping where day = train_info.day_of_departure)
                   )
              )
              or
              (
                t1.destination_station_name = train_info.source_station_name
                and (
                     (select daynum from day_mapping where day = t1.day_of_arrival) =
                     (select daynum from day_mapping where day = train_info.day_of_departure)
                    )
                and ( t1.arrival_time <= train_info.departure_time )
              )
            )
        )
),
t3 as
(
    select train_info.destination_station_name, train_info.day_of_arrival, train_info.arrival_time
     from (train_info join t2
           on ( (
                  t2.destination_station_name = train_info.source_station_name
                  and (
                       (select daynum from day_mapping where day = t2.day_of_arrival) <
                       (select daynum from day_mapping where day = train_info.day_of_departure)
                      )
                )
                or
                (
                  t2.destination_station_name = train_info.source_station_name
                  and (
                       (select daynum from day_mapping where day = t2.day_of_arrival) =
                       (select daynum from day_mapping where day = train_info.day_of_departure)
                      )
                  and (t2.arrival_time <= train_info.departure_time )
                )
              )
          )
)
select a.destination_station_name
from (select t1.destination_station_name from t1
      UNION
      select t2.destination_station_name from t2
      UNION
      select t3.destination_station_name from t3
    ) as a
where (a.destination_station_name <> 'DADAR')
order by a.destination_station_name;


--5-- 
with recursive ans as (
select source_station_name, destination_station_name, 0 as hops ,0 as count 
from train_info 
where source_station_name = 'CST-MUMBAI' and destination_station_name = 'VASHI' 

union 

select train_info.source_station_name,train_info.destination_station_name,ans.hops+1,count+1 from train_info,ans
where ans.source_station_name = 'CST-MUMBAI' and train_info.destination_station_name = 'VASHI'and ans.hops <2)
select count from ans 


--6--


--7--


--8--


--9--


--10--


--11--



--12--



--13--




--14--



--15--




--16--


--17--


--18--


--19--


--20--


--21--


--22--





























