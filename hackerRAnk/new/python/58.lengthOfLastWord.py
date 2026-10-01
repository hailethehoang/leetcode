class Solution:
    def lengthOfLastWord(self, s: str) -> int:
        text = s.strip()
        for index, char in reversed(list(enumerate(text))):
            if char == ' ':
                return len(text) - 1 - index
        return 0

    def lengthOfLastWord2(self, s: str) -> int:
        text = s.strip()
        last_space = text.rfind(' ')
        if last_space != -1:
            return len(text) - 1 - last_space
        return 0

    def lengthOfLastWord(self, s: str) -> int:
        words = s.strip().split()
        if not words:
            return 0
        return len(words[-1])