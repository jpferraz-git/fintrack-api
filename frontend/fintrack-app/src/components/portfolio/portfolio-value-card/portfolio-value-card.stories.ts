import type { Meta, StoryObj } from '@storybook/angular';
import { PortfolioValueCard } from './portfolio-value-card.component';

const meta: Meta<PortfolioValueCard> = {
  title: 'Components/PortfolioValueCard',
  component: PortfolioValueCard,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<PortfolioValueCard>;

export const Primary: Story = {
  args: {},
};
