# Practice: start with these three exercises
# Exercise 1 — Predict the output without running it
a = [1, 2]
b = a
c = a.copy()

b.append(3)
c.append(4)

print(a) # [1,2,3]
print(b) # [1,2,3]
print(c) # [1,2,4]
print(a == b) # True
print(a is b) # True
print(a is c) # False

# Exercise 2 — Collections and loops
# Given:
# nums = [4, 2, 4, 7, 2, 9, 10]
# Write code to produce:
# # Unique numbers, sorted:
# [2, 4, 7, 9, 10]
# # Even numbers, preserving duplicates and order:
# [4, 2, 4, 2, 10]
# # Frequency of every number:
# {4: 2, 2: 2, 7: 1, 9: 1, 10: 1}

nums = [4, 2, 4, 7, 2, 9, 10]
nums_set = set()
sort_num = sorted(nums)
# nums_set = { c for c in sort_num }
unique_sorted = sorted(set(nums))
# [2, 4, 7, 9, 10]

even_nums = [ n for n in nums if n % 2 == 0]


freq = {}
for n in nums:
    if freq.get(n) is None:
        freq[n] = 1
    else:
        freq[n] += 1
# shorten: 
freq = {}
for n in nums:
    freq[n] = freq.get(n, 0) + 1

# counts = { n: counts.get(n, 0) + 1 for n in nums }     

print(freq)
# print(counts)
# print(freq == counts)

# Hint for counting:
# counts[number] = counts.get(number, 0) + 1

# Exercise 3 — Backend-style aggregation
transactions = [
    {"customer": "A", "amount": 100},
    {"customer": "B", "amount": 50},
    {"customer": "A", "amount": 200},
    {"customer": "B", "amount": -10},
    {"customer": "C", "amount": 0},
]

# Implement:
def summarize_transactions(transactions):
    balances = {}
    for c in transactions:
        if c["amount"] >= 0:
            balances[c["customer"]] = balances.get(c["customer"], 0) + c["amount"]
    return balances

print(summarize_transactions(transactions))

# Requirements:

# Sum amounts by customer.
# Ignore negative amounts.
# Keep customers whose amount is zero.
# Return {} for an empty input.
# Assume all records contain valid customer strings and integer amounts.

# Expected result:

# {"A": 300, "B": 50, "C": 0}

# # Start by sending your predictions for Exercise 1, and we’ll check your understanding of references before moving to aggregation.
# Next exercise: extend your function to return both the total and the number of accepted transactions per customer:

# {
#     "A": {"total": 300, "count": 2},
#     "B": {"total": 50, "count": 1},
#     "C": {"total": 0, "count": 1},
# }

# Keep ignoring negative amounts. This will give you practice with nested dictionaries.
def summarize_transactions(transactions):
    balances = {}
    for c in transactions:
        customer = c["customer"]
        amount = c["amount"]
        if amount >= 0:
            if customer not in balances:
                balances[customer] = {"total": 0, "count": 0}

            balances[customer] = {
                "total": balances[customer]["total"] + amount,
                "count": balances[customer]["count"] + 1
            }
    return balances