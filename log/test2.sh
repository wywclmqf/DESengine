#!/bin/bash


rm -rf ../logDesis/output*
rm -rf ../logDesisSW/output*
rm -rf ../logDesisIC/output*
rm -rf ../logDesisCen/output*


for i in 10 50 100 200 500 1000 2000 5000 10000

do

echo $i 


srun -A rabl -p magic -w node-04 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EntryPointForLocalNode 4 $i 41 4 > ../logDesis/outputlocalDesis$i &

srun -A rabl -p magic -w node-03 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode 2 $i 41 4 > ../logDesis/outputinterDesis$i &

srun -A rabl -p magic -w node-02 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EnteryPointForRootnode 1 $i 41 4 > ../logDesis/outputrootDesis$i &



srun -A rabl -p magic -w node-07 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 $i 41 4 > ../logDesisSW/outputlocalSWDesis$i &

srun -A rabl -p magic -w node-06 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 $i 41 4 &

srun -A rabl -p magic -w node-05 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 $i 41 4 > ../logDesisSW/outputrootSWDesis$i &



srun -A rabl -p magic -w node-10 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 $i 41 4 > ../logDesisIC/outputlocalICDesis$i &

srun -A rabl -p magic -w node-09 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 $i 41 4 &

srun -A rabl -p magic -w node-08 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 $i 41 4 > ../logDesisIC/outputrootICDesis$i &


srun -A rabl -p magic -w node-12 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 $i 41 4 > ../logDesisCen/outputlocalCenDesis$i &

srun -A rabl -p magic -w node-11 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 $i 41 4 > ../logDesisCen/outputrootCenDesis$i &


sleep 300

sleep 60

done

echo END!!!
