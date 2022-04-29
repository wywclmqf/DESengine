#!/bin/bash

for i in 1 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputScotty$i

srun -A rabl -p magic --mem 40G -w node-08 --cpus-per-task=20 ../tools/java/bin/java -Xms4096M -Xmx40960M -cp ../Desis.jar De.Hpi.Scotty.CSVReading $i > ../log/outputScotty$i &

sleep 300

scancel -u wang.yue

sleep 30
done

echo END!!!
