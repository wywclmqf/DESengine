#!/bin/bash

for i in 1 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputlocalDisco$i
rm -rf ../log/outputinterDisco$i
rm -rf ../log/outputrootDisco$i

srun -A rabl -p magic -w node-04 --mem 20G --cpus-per-task=20 ../tools/java/bin/java -Xms2096M -Xmx20240M -cp ../Desis.jar De.Hpi.Disco.Source.executables.DistributedChildMain node-02 40010 40020 30010 1 1 > ../log/outputlocalDisco$i &

srun -A rabl -p magic -w node-02 --mem 20G --cpus-per-task=20 ../tools/java/bin/java -Xms2096M -Xmx20240M -cp ../Desis.jar De.Hpi.Disco.Source.executables.DistributedMergeNodeMain node-08 50010 50020 40010 40020 2 1 > ../log/outputinterDisco$i &

srun -A rabl -p magic -w node-08 --mem 20G --cpus-per-task=20 ../tools/java/bin/java -Xms2096M -Xmx20240M -cp ../Desis.jar De.Hpi.Disco.Source.executables.DistributedRootMain $i 50010 50020 "/hpi/fs00/home/wang.yue/data/result" 10 2 > ../log/outputrootDisco$i &

sleep 300

scancel -u wang.yue

sleep 30
done

echo END!!!
