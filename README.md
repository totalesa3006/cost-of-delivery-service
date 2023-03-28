# cost-of-delivery-service
Cost Of Delivery Service is an API that will calculate the cost of delivery of a parcel based on weight and volume
(volume = height * width * length). The API should accept the following:
1. Weight (kg)
2. Height (cm)
3. Width (cm)
4. Length (cm)
   The rules for calculating the cost of delivery are in order of priority:

-------------------------------------------------------------------------------------
Priority          Rule Name      Condition                        Cost

-------------------------------------------------------------------------------------
1 (Highest)       Reject         Weight exceeds 50kg               N/A
2                 Heavy Parcel   Weight exceeds 10kg               PHP 20 * Weight (kg)
3                 Small Parcel   Volume is less than 1500 cm3      PHP 0.03 * Volume
4                 Medium Parcel  Volume is less than 2500 cm3      PHP 0.04 * Volume
5 (Lowest)        Large Parcel   Volume is greater then 2500cm3    PHP 0.05 * Volume


---------------------------------------------------------------------------------------


For this API implementation the rules has been saved in Java in memory database.

Following design patterns has been used.

1. Factory pattern
2. Builder Pattern
3. Stratergy Pattern ( This is applied dynamicaly after feacthing Rules configuration from database.)


