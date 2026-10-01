print("Hello")

for i in range(3):
    print(i)  # 0, 1, 2

for i in range(2, 8, 2):
    print(i)  # 2, 4, 6

    remaining = 3

while remaining > 0:
    remaining -= 1

def greet(name: str, prefix: str = "Hello") -> str:
    return f"{prefix}, {name}!"

print(greet("Quang"))
print(greet("Quang", "Hi"))
print(greet(name="Quang", prefix="Hi"))
print(greet("name", prefix="Nuce"))


def min_max(nums):
    return min(nums), max(nums)

smallest, largest = min_max([3, 1, 9])
print(largest)


def add_item(item, items=None):
    if items is None:
        items = []

    items.append(item)
    return items