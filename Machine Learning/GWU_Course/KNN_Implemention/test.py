import csv
def scale(arrays):
	max = float('-inf')
	min = float('inf')
	for x in arrays:
		if float(x) > max:
			max = float(x);
		if float(x) < min:
			min = float(x);
	medium = (max + min)/2
	length = len(arrays)
	mylist = []
	for i in range(length):
		mediate = float(arrays[i])/medium
		mylist.append(mediate);
	return mylist;
with open('diabetes.csv') as csv_file:
	csv_reader = csv.reader(csv_file,delimiter=',')
	line_count = 0
	for row in csv_reader:
		if line_count == 0:
			print(f'Column names are {", ".join(row)}')
			line_count += 1
		else:
			print(f'{",".join(row)}')
			line_count += 1
		print(f'Processed {line_count} lines.')
