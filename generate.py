from random import randrange

#size
width = 12
height = 8

#left = 0, down = 1, right = 2, up = 3
cur_dir = 0 

#random seed
start = randrange(8)

#create tilemap
level = [[0 for x in xrange(height)] for x in xrange(width)]

cur_pos = (0,start)

def init():
    level = [[0 for x in xrange(height)] for x in xrange(width)]
    level[0][start] = 1

def generate():
    dir = 0

def has_adjacent(x, y):
    adj = 0
    try:
        if (level[x - 1][y] == 0):
            adj += 1
        if (level[x][y + 1] == 0):
            adj += 1
        if (level[x][y - 1] == 0):
            adj += 1
        if (level[x + 1][y] == 0):
            adj += 1

    if adj == 3:
        return false

    return true

def choose_dir():
    dirs = set([0,1,2,3])

    #eliminate x
    if (cur_pos[0] == 0):
        dirs.remove(0)
    else if (cur_pos[0] == width - 1):
        dirs.remove(2)
    else:


    if (cur_pos[1] == 0):
        dirs.remove(3)
    else if (cur_pos[1] == height - 1):
        dirs.remove(1)




def main():
    init()
    generate()

if __name__ == "__main__":
    main()
